package com.sjb.sjbProject.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OrderConfirmationRequest {
	private String orderId;


}
