package com.sjb.sjbProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjb.sjbProject.bean.IdGenerator;

import com.sjb.sjbProject.bean.Room;
import com.sjb.sjbProject.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepo;

	IdGenerator idg = new IdGenerator();

	public Room insertRoom(Room room) {

		Room rs = roomRepo.save(room);

		if (rs == null) {
			System.out.println("insertRoom is null");
			return null;
		}

		return rs;
	}

	public Room findRoomById(String productID, Integer hotelID) {
//		System.out.println("############ ProductService ###############");
		Room result = roomRepo.findRoomById(productID, hotelID);
//		System.out.println("############ ProductService end ###############");
		return result;
	}

	public List<Room> findAllRoom(Integer hotelID) {
		return roomRepo.findAllRoom(hotelID);
	}

	public String deleteRoomById(String productID, Integer hotelID) {
		Integer c = roomRepo.deleteByProductIDAndHotelID(productID, hotelID);
		if (c > 0) {
			return "delete success";
		}
		return "delete fail";
	}

}
