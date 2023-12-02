package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.Cart;
import com.sjb.sjbProject.bean.Customer;
import com.sjb.sjbProject.service.CartService;
import com.sjb.sjbProject.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("cart/addcart")
    public ResponseEntity<String> insertCart(@ModelAttribute Cart cart,
                                             HttpSession httpSession) {
        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");

        if(loginID == null){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("請先登入");
        }

        Cart queryResult = cartService.findByLoginIDAndProductID(loginID, cart.getProductID());
        if (queryResult != null) {
            queryResult.setQuantity(queryResult.getQuantity() + cart.getQuantity());
            cartService.insertCart(queryResult);
            return ResponseEntity.ok("插入成功");
        }
        try {


            cart.setLoginID(loginID);

            Customer byId = customerService.findById(loginID);
            cart.setCustomer(byId);

            cartService.insertCart(cart);

            return ResponseEntity.ok("插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("發生錯誤: " + e.getMessage());
        }
    }

//    ================== 查詢 ====================

    @GetMapping("/cart/findAll")
    public List<Cart> findAllByLoginID(HttpSession httpSession) {
        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
        if(loginID == null){
            return null;
        }

        return cartService.findAllByLoginID(loginID);
    }


    //    ================== 修改 ====================
    @GetMapping("/cart/updateGet")
    public Cart updateByCartIDget(@RequestParam("cartID") Integer cartID) {
        return cartService.findByCartID(cartID);
    }

    @PutMapping("/cart/updatePost")
    public Cart updateCart(@ModelAttribute Cart cart, HttpSession httpSession) {
        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");

        cart.setLoginID(loginID);

        Customer byId = customerService.findById(loginID);
        cart.setCustomer(byId);

        return cartService.insertCart(cart);
    }


//    ================== 刪除 ====================

    @DeleteMapping("/cart/delete")
    public Integer deleteByCartID(@RequestParam("cartID") Integer cartID) {
        return cartService.deleteByCartID(cartID);
    }

}
