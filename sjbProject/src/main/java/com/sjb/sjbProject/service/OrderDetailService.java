package com.sjb.sjbProject.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjb.sjbProject.bean.OrderDetail;
import com.sjb.sjbProject.repository.OrderDetailRepository;

import java.util.List;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> findOrderDetailsByOrderID(String orderID) {
        return orderDetailRepository.findByOrderOrderID(orderID);
    }

    public void deleteOrderDetail(Integer detailID) {
        orderDetailRepository.deleteById(detailID);
    }

    // 其他与订单细节相关的业务逻辑方法
}
