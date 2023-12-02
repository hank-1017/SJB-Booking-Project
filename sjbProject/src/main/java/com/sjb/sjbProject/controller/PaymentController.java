package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.IdGenerator;
import com.sjb.sjbProject.bean.OrderDetail;
import com.sjb.sjbProject.bean.OrderTable;
import com.sjb.sjbProject.service.CustomerService;
import com.sjb.sjbProject.service.OrderTableService;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutDevide;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true") // 跨域請求設定
public class PaymentController {
    @Autowired
    private OrderTableService orderService;
    @Autowired
    private CustomerService customerService;

    @Value("${myngrok.url}")
    private String myngrokURL;

    @GetMapping("/goECPay")
    public void goECPay(HttpServletResponse response, HttpSession httpSession) throws IOException {
        OrderTable order = (OrderTable) httpSession.getAttribute("payOrder");
        OrderDetail orderDetail = order.getOrderDetails().get(0);

        //設定金流
        AllInOne aio = new AllInOne("");
        AioCheckOutOneTime aioCheck = new AioCheckOutOneTime();
        //特店編號
        aioCheck.setMerchantID("3002607");
        //特店交易時間
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setLenient(false);
        aioCheck.setMerchantTradeDate(sdf.format(new Date()));
        //交易金額
        aioCheck.setTotalAmount(orderDetail.getUniPrice());
        //交易描述
        aioCheck.setTradeDesc("SJB.com訂房付款");
        //商品名稱
        aioCheck.setItemName(orderDetail.getProductID());
        //訂單編號
        aioCheck.setMerchantTradeNo(order.getOrderID());
        //付款完成後後端接收付款結果網址
        aioCheck.setReturnURL(myngrokURL+"/sjb/orders/returnURL"); // 這裡要改成ngrok的網址
        //Client端回傳付款結果網址
//        aioCheck.setOrderResultURL("http://localhost:5173/orders/paymentResult");
        //導向前端付款結果
        aioCheck.setClientBackURL("http://localhost:5173/orders/paymentResult");


        //輸出畫面
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        out.print(aio.aioCheckOut(aioCheck, null));
    }

    //綠界回傳
    @PostMapping("/returnURL")
    public String returnURL(@RequestParam("MerchantTradeNo") String MerchantTradeNo,
                            @RequestParam("RtnCode") int RtnCode,
                            @RequestParam("TradeAmt") int TradeAmt,
                            @RequestParam("PaymentDate") String PaymentDate) throws IOException {
        //交易成功
        if (RtnCode == 1) {
            Optional<OrderTable> order = orderService.getOrderById(MerchantTradeNo);
            if (order.isEmpty()) {
                System.out.println("訂單不存在");
                return "0|err";
            }
            OrderTable orderTable = order.get();
            orderTable.setOrderStatus("已付款");
            orderTable.setUpdateTime(PaymentDate);
            orderService.updateOrder(order.get());
            System.out.println("訂單編號: " + MerchantTradeNo + " 交易成功，金額：" + TradeAmt);
            return "1|OK";
        } else {
            System.out.println("交易失敗");
            return "0|err";
        }
    }

    @GetMapping("/paymentResult")
    public ResponseEntity<Map<String, String>> paymentResult(HttpSession httpSession) {
        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
        OrderTable sessionOrder = (OrderTable) httpSession.getAttribute("payOrder");
        if (loginID == null || sessionOrder.getOrderID() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        OrderTable order = orderService.getOrderById(sessionOrder.getOrderID()).get();
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        Map<String, String> paymentResult = new HashMap<>();
        paymentResult.put("orderID", order.getOrderID());
        paymentResult.put("orderStatus", order.getOrderStatus());
        paymentResult.put("paymentTime", order.getUpdateTime());
        paymentResult.put("TradeAmt", orderDetail.getUniPrice());
        System.out.println("get paymentResult: " + loginID);
        return ResponseEntity.ok(paymentResult);
    }


//    @PostMapping("/showHistoryOrder")
//    public String showHistoryOrder(HttpSession httpSession) {
//        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
//        if (loginID == null) {
//            return "用戶未登入";
//        }
//        List<OrderTable> customerOrders = orderService.getOrdersByCustomerIDByOrderDetailsDetailIDDesc(loginID);
//        return customerOrders.toString();
//    }

}
