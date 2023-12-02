package com.sjb.sjbProject.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.Room;
import com.sjb.sjbProject.service.SearchService;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true") // 跨域請求設定
public class SearchController {
	 
	@Autowired
	private SearchService searchService;
	
	
	 @GetMapping("/findHotelsByCountry")
	    public List<Hotel> searchHotelsByCountry(@RequestParam String country) {
	        return searchService.searchHotelsByCountry(country);
	    }
	 @GetMapping("/findHotelsByCity")
	 public List<Hotel> searchHotelsByCity(@RequestParam String city) {
	     return searchService.searchHotelsByCity(city);
	 }
	 
	 @GetMapping("/findRoomsByCityAndCapacityAndDateRange")
	 public List<Room> searchRoomsByCityAndCapacityAndDateRange(
	     @RequestParam String city,
	     @RequestParam int requiredCapacity,
	     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
	     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
	 ) {
	     return searchService.searchRoomsByCityAndCapacityAndDateRange(city, requiredCapacity, startDate, endDate);
	 }
		//1024庭安
		
		@GetMapping("/room/roomsByHotel")
		public List<Room> findRoomsByHotel(@RequestParam(value = "hotelID", required = false) Integer hotelID) {
		    if (hotelID != null) {
		        List<Room> rooms = searchService.findAllRoom(hotelID);
		        return rooms;
		    } else {
		        // 返回适当的响应，例如参数无效的错误消息
		        return new ArrayList<>(); // 或者其他适当的响应
		    }
		}
		 //1024庭安
	    @GetMapping("/hotel/getAll")
	    public List<Hotel> getAllOrders() {
	        return searchService.findAllHotel();
	    }
	    @GetMapping("/room/getRoomById")
	    public Room getRoomById(@RequestParam(value = "productID") String productID) {
	        return searchService.getRoomById(productID);
	    }
	    @GetMapping("/details/{hotelID}")
	    public ResponseEntity<?> viewHotelDetails(@PathVariable int hotelID) {
	        Hotel hotel = searchService.findHotelById(hotelID);
	        if (hotel != null) {
	            return ResponseEntity.ok(hotel); // 返回酒店信息
	        } else {
	            return ResponseEntity.notFound().build(); // 如果找不到酒店，返回404 Not Found
	        }
	    }
	    
	    //前端搜尋使用
	    @GetMapping("/hotelByDate")
	    public ResponseEntity<List<Hotel>> searchHotelsByDateRange
	    (  
	    	 @RequestParam String city,
	   	     @RequestParam int requiredCapacity,
		     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
		     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
	        List<Hotel> hotels = searchService.findHotelsByDateRange(city, requiredCapacity, startDate, endDate);
	        return new ResponseEntity<>(hotels, HttpStatus.OK);
	    }

}
