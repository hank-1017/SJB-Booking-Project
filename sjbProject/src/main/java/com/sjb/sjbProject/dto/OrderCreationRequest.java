package com.sjb.sjbProject.dto;

import java.util.List;

import lombok.Data;
@Data
public class OrderCreationRequest {
    private String customerName; // 顾客姓名
    private List<OrderDetailRequest> orderDetails; // 订单细节列表

    // 构造函数、getter 和 setter 略
}
