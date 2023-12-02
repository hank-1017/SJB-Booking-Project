package com.sjb.sjbProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjb.sjbProject.bean.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderOrderID(String orderID);
}
