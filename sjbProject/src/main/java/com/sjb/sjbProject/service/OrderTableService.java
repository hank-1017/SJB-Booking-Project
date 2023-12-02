package com.sjb.sjbProject.service;

import com.sjb.sjbProject.bean.OrderTable;
import com.sjb.sjbProject.repository.OrderTableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderTableService {

    @Autowired
    private OrderTableRepository orderRepository;

    public OrderTable createOrder(OrderTable order) {
        // 其他創建訂單的邏輯
        // 保存訂單
        return orderRepository.save(order);
    }
   

    public void deleteOrder(String orderID) {
        orderRepository.deleteById(orderID);
    }

    public List<OrderTable> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderTable> getOrderById(String orderID) {
        return orderRepository.findById(orderID);
    }

    public OrderTable updateOrder(OrderTable order) {
        // 在這裡執行更新邏輯
        // 使用 orderRepository 來將更新後的對象保存到資料庫中
        return orderRepository.save(order);
    }
    public List<OrderTable> getOrdersByCustomerID(Integer customerID) {
        return orderRepository.findByCustomerLoginID(customerID);
    }
    public List<OrderTable> getOrdersByCustomerIDByOrderDetailsDetailIDDesc(Integer customerID) {
        return orderRepository.findByCustomerLoginIDOrderByOrderDetailsDetailIDDesc(customerID);
    }
    
    //1104
    public List<OrderTable> getAllOrdersSortedByCheckInDate() {
        try {
            // 在這裡編寫查詢資料庫以按入住日期排序的代碼
            // 你可以使用適合你的持久層（如Spring Data JPA或Hibernate）來執行查詢
            // 以下是一个示例，使用Spring Data JPA的Repository來查詢
            return orderRepository.findAllOrderByCheckInTime();
        } catch (Exception e) {
            // 處理異常
            throw new RuntimeException("獲取已排序的訂單時發生錯誤: " + e.getMessage());
        }
    }
    public List<OrderTable> getOrdersByHotelID(int hotelID) {
        // 使用 Spring Data JPA 的方法来查询数据库
        List<OrderTable> orders = orderRepository.findOrdersByHotelID(hotelID);

        return orders;
    }
    
    public OrderTable findOrderById(String orderID) {
        Optional<OrderTable> order = orderRepository.findById(orderID);
        if (order.isPresent()) {
            return order.get();
        } else {
            // 可以处理订单不存在的情况，例如抛出异常
            throw new RuntimeException("订单不存在");
        }
    }
}
