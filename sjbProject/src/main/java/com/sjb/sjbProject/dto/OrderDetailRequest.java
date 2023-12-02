package com.sjb.sjbProject.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OrderDetailRequest {
    private String productID;
    private String uniPrice;
    private Integer quantity;
    private String payment;
    private Integer numPeople;
    private Date checkInTime;
    private Date checkOutTime;
    private String evaluation;
    private String comment;
    private String orderName;
    private String orderPhone;
}
