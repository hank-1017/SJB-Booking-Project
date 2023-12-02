package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.IdGenerator;
import com.sjb.sjbProject.bean.OrderDetail;
import com.sjb.sjbProject.bean.OrderTable;
import com.sjb.sjbProject.bean.Room;
import com.sjb.sjbProject.dto.OrderConfirmationRequest;
import com.sjb.sjbProject.dto.OrderCreationRequest;
import com.sjb.sjbProject.dto.OrderDetailRequest;
import com.sjb.sjbProject.service.CustomerService;
import com.sjb.sjbProject.service.MailService;
import com.sjb.sjbProject.service.OrderTableService;
import com.sjb.sjbProject.service.SearchService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true") // 跨域請求設定

public class OrderTableController {

	@Autowired
	private OrderTableService orderService;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private MailService mailService;

	
	@Transactional
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@RequestBody OrderCreationRequest orderCreationRequest, HttpSession httpSession) {
	    Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
		System.out.println("loginID"+loginID);
	    if (loginID == null || loginID == 0) {
	        return new ResponseEntity<>("用戶未登入", HttpStatus.UNAUTHORIZED);
	    }

	    // 創建訂單對象
	    OrderTable order = new OrderTable();
	    order.setOrderID(idGenerator.generateId()); 
	    order.setOrderStatus("待付款-接收中"); // 設置訂單狀態為待處理
	    order.setOrderTime(LocalDateTime.now().toString()); // 設置訂單時間為當前時間
	    order.setUpdateTime(LocalDateTime.now().toString());
	    order.setCustomer(customerService.findById(loginID)); // 關聯顧客

	    // 創建訂單細節對象
	    List<OrderDetail> orderDetails = new ArrayList<>();
	    for (OrderDetailRequest detailRequest : orderCreationRequest.getOrderDetails()) {
	        OrderDetail orderDetail = new OrderDetail();
	        orderDetail.setProductID(detailRequest.getProductID());
	        orderDetail.setUniPrice(detailRequest.getUniPrice());
	        orderDetail.setQuantity(detailRequest.getQuantity());
	        orderDetail.setPayment(detailRequest.getPayment());
	        orderDetail.setNumPeople(detailRequest.getNumPeople());
	        orderDetail.setCheckInTime(detailRequest.getCheckInTime());
	        orderDetail.setCheckOutTime(detailRequest.getCheckOutTime());
	        orderDetail.setEvaluation(detailRequest.getEvaluation());
	        orderDetail.setComment(detailRequest.getComment());
	        orderDetail.setOrderName(detailRequest.getOrderName());
	        orderDetail.setOrderPhone(detailRequest.getOrderPhone());
	        
	        orderDetail.setOrder(order); // 關聯訂單
	        
	        orderDetails.add(orderDetail);
	     // 在創建訂單時更新Room對象的orders屬性
	       
	        Room room = searchService.findRoomByProductID(detailRequest.getProductID()); // 根據productID找到對應的Room
	        if (room != null) {
	            room.getOrders().add(order); // 將訂單添加到Room的orders集合中
	            order.getRooms().add(room);
	        }
			httpSession.setAttribute("payOrder", order);

	    }

	    // 設置訂單的訂單細節
	    order.setOrderDetails(orderDetails);
	 
	    // 保存訂單和訂單細節
	    orderService.createOrder(order);
	    // 發送電子郵件通知
	    String customerEmail = customerService.findById(loginID).getEmail(); // 獲取客戶的電子郵件
	    String emailSubject = "訂單創建通知";
	    String emailMessage = "您的訂單已成功創建。"; // 自定義通知消息

	    mailService.sendMail(customerEmail, emailSubject, emailMessage);

	    return new ResponseEntity<>("訂單已生成", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/createFromCart")
	public ResponseEntity<?> createOrderFromCart(@RequestBody List<OrderDetail> orderDetails, HttpSession httpSession) {
		Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
		System.out.println("loginID"+loginID);
		if (loginID == null || loginID == 0) {
			return new ResponseEntity<>("用戶未登入", HttpStatus.UNAUTHORIZED);
		}

		// 創建訂單對象
		OrderTable order = new OrderTable();
		order.setOrderID(idGenerator.generateId());
		order.setOrderStatus("待付款-接收中"); // 設置訂單狀態為待處理
		order.setOrderTime(LocalDateTime.now().toString()); // 設置訂單時間為當前時間
		order.setUpdateTime(LocalDateTime.now().toString());
		order.setCustomer(customerService.findById(loginID)); // 關聯顧客

		// 創建訂單細節對象

		for (OrderDetail detail : orderDetails) {
			detail.setOrder(order); // 關聯訂單

			System.out.println("=============");
			System.out.println(detail.getQuantity());
			System.out.println(detail.getOrderPhone());
			System.out.println(detail.getComment());
			System.out.println(detail.getCheckInTime());
			System.out.println("=============");
			// 在創建訂單時更新Room對象的orders屬性

			Room room = searchService.findRoomByProductID(detail.getProductID()); // 根據productID找到對應的Room
			if (room != null) {
				room.getOrders().add(order); // 將訂單添加到Room的orders集合中
				order.getRooms().add(room);
			}
			httpSession.setAttribute("payOrder", order);

		}

		// 設置訂單的訂單細節
		order.setOrderDetails(orderDetails);

		// 保存訂單和訂單細節
		orderService.createOrder(order);
		// 發送電子郵件通知
		String customerEmail = customerService.findById(loginID).getEmail(); // 獲取客戶的電子郵件
		String emailSubject = "訂單創建通知";
		String emailMessage = "您的訂單已成功創建。"; // 自定義通知消息

		mailService.sendMail(customerEmail, emailSubject, emailMessage);

		return new ResponseEntity<>("訂單已生成", HttpStatus.OK);
	}


	@GetMapping("/get/{orderID}")
	public Optional<OrderTable> getOrder(@PathVariable String orderID) {
		 System.out.println("Received orderID: " + orderID);
		return orderService.getOrderById(orderID);
	}

	@GetMapping("/getAll")
	public List<OrderTable> getAllOrders() {
		return orderService.getAllOrders();
	}

	@PutMapping("/update")
	public OrderTable updateOrder(@RequestBody OrderTable order) {
		return orderService.updateOrder(order);
	}

	@DeleteMapping("/delete/{orderID}")
	public void deleteOrder(@PathVariable String orderID) {
		orderService.deleteOrder(orderID);
	}
	@GetMapping("/ordersByCustomerID")
	public ResponseEntity<?> findOrdersByCustomerIDByOrderDetailsDetailIDDesc(HttpSession httpSession) {

		int loginID=(Integer) httpSession.getAttribute("customerLoginID");
		System.out.println("####################################");
		System.out.println(loginID);
		System.out.println("####################################");
		if (loginID != 0) {
			List<OrderTable> customerOrders = orderService.getOrdersByCustomerIDByOrderDetailsDetailIDDesc(loginID);
			return new ResponseEntity<List<OrderTable>>(customerOrders, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("用戶未登入", HttpStatus.UNAUTHORIZED);
		}
	}
	
	//1104
	@GetMapping("/getAllSortedByCheckInDate")
	public ResponseEntity<?> getAllOrdersSortedByCheckInDate() {
	    try {
	        // 調用服務層方法来獲取已排序的訂單列表
	        List<OrderTable> orders = orderService.getAllOrdersSortedByCheckInDate();

	        if (orders.isEmpty()) {
	            return new ResponseEntity<>("没有訂單可用", HttpStatus.OK);
	        }

	        return new ResponseEntity<List<OrderTable>>(orders, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("獲取已排序的訂單時發生錯誤: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//1104目前不曉得要透過什麼來輸入hotelID
	@GetMapping("/getOrdersByHotelID")
	public ResponseEntity<?> getOrdersByHotelID(HttpSession httpSession) {
	    // 检查用户是否已登录
		Integer hotelID=(Integer) httpSession.getAttribute("hotelID");
	    if (httpSession.getAttribute("loginId") == null) {
	        return new ResponseEntity<>("用戶登入", HttpStatus.UNAUTHORIZED);
	    }

	    try {
	        // 用戶已登入，繼續獲取符合條件的訂單
	        List<OrderTable> orders = orderService.getOrdersByHotelID(hotelID);

	        if (orders.isEmpty()) {
	            return new ResponseEntity<>("没有符合條件的訂單", HttpStatus.OK);
	        }

	        return new ResponseEntity<List<OrderTable>>(orders, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("獲取符合條件的訂單時發生錯誤: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//1105
	// 在酒店後台API中，接收訂單信息並確認訂單
	@PostMapping("/confirmOrder")
	public ResponseEntity<?> confirmOrder(@RequestBody OrderConfirmationRequest orderConfirmationRequest) {
	    // 根據訂單信息查詢訂單紀錄
	    OrderTable order = orderService.findOrderById(orderConfirmationRequest.getOrderId());

	    if (order == null) {
	        return new ResponseEntity<>("訂單不存在", HttpStatus.NOT_FOUND);
	    }
	    Date currentDate = new Date();
	    // 更新訂單狀態為"已建立"
	    order.setOrderStatus("已建立-待付款");
	    order.setUpdateTime(currentDate.toString());
	    orderService.updateOrder(order);

	    return new ResponseEntity<>("訂單已建立", HttpStatus.OK);
	}
	
	//1107取消訂單
	  @PatchMapping("/cancel/{orderID}")
	    public ResponseEntity<String> cancelOrder(@PathVariable String orderID) {
	        try {
	            // 通過訂單 ID 查找訂單
	            OrderTable order = orderService.findOrderById(orderID);

	            if (order == null) {
	                return new ResponseEntity<>("訂單不存在", HttpStatus.NOT_FOUND);
	            }

	            // 更新訂單狀態為 "已取消"
	            order.setOrderStatus("已取消");
	            orderService.updateOrder(order);

	            return new ResponseEntity<>("訂單已取消", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("取消訂單時發生錯誤: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	  
	  
	
}
