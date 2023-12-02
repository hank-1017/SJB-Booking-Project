package com.sjb.sjbProject.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MessageDto {
	private Integer hotelID;
	private String hotelName;
}
