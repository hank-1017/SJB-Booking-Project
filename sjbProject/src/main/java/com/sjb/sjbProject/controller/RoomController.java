package com.sjb.sjbProject.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.IdGenerator;
import com.sjb.sjbProject.bean.Room;
import com.sjb.sjbProject.service.HotelService;
import com.sjb.sjbProject.service.RoomService;

import jakarta.servlet.http.HttpSession;


@RestController
@CrossOrigin
public class RoomController {

	@Autowired
	private RoomService rService;
	@Autowired
    private HotelService hotelService;

	@PostMapping("/room/insertRoom")
	public Room insertRoom(@ModelAttribute Room room,	Model model,HttpSession session) {
		IdGenerator idg = new IdGenerator();
		String id = idg.generateId();
	
		Integer hotelID = (Integer) session.getAttribute("hotelID");

		if (room.getDiscount() == null){
			room.setDiscount(1.0);
		}

		room.setHotelID(hotelID);
		
		room.setProductID(id);
		room.setScore(0);
		room.setEnableSwitch(true);
		
		return rService.insertRoom(room);
	}
//	-----------------------------------------
	 
	@GetMapping("/room/findRoomById")
	public Room findRoomById(@RequestParam(name = "hotelID") Integer hotelID,@RequestParam(name = "productID")String productID) {
		System.out.println("############ @RestController findRoomById ###############");
		
		Room result = rService.findRoomById(productID, hotelID);
		if(result==null) {
			System.out.println("null");
			return new Room();
		}
		return result;
	}

	@GetMapping("/room/findAllRoom")
	public List<Room> findAllRoomjson(HttpSession session,Model model) {
		Integer hotelID = (Integer) session.getAttribute("hotelID");

		if(hotelID == null) {
			return new ArrayList<Room>();
		}

		return rService.findAllRoom(hotelID);
	}


	@DeleteMapping("/room/delete")
	public String deleteRoomById(@RequestParam(name = "hotelID") Integer hotelID,@RequestParam(name = "productID")String productID) {

		return rService.deleteRoomById(productID, hotelID);
	}
	
// ---------------------------------------------

	@GetMapping("/room/updateRoomGet")
	public Room updateRoomByIdget(@RequestParam(name = "hotelID") Integer hotelID,@RequestParam(name = "productID")String productID,Model model) {

		return rService.findRoomById(productID, hotelID);
	}

	@PutMapping("/room/updateRoom")
	public Room updateRoomById(@ModelAttribute Room room ) {
		if(room.getEnableSwitch()==null){
			room.setEnableSwitch(false);
		}


		return rService.insertRoom(room);
	}


// 跳頁：房間管理的選擇飯店
	// ----------------------------------------------------------------------------------------
	@GetMapping("/room/setSessionHotelID")
	public String setSessionHotelID(@RequestParam(name = "hotelID") Integer hotelID, HttpSession session) {

		session.setAttribute("hotelID", hotelID);
		System.out.println(hotelID);
		
		return "redirect:/room/findAllRoom";
	}

	@GetMapping("/hotel/findAllhoteljson")
	public List<Hotel> findAllHotel(HttpSession httpSession, Model model) {
		if (httpSession.getAttribute("loginId") == null) {
			return new ArrayList<Hotel>();
		}
		Object hotelAttribute = httpSession.getAttribute("loginId");
		Integer loginId = (Integer) hotelAttribute;
		List<Hotel> hotels = hotelService.findByLoginid(loginId);
		System.out.println(loginId);
		model.addAttribute("hotels", hotels);
		return hotels;
	}
	
}
