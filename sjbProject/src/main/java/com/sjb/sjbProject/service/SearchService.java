package com.sjb.sjbProject.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.HotelDetail;
import com.sjb.sjbProject.bean.Room;
import com.sjb.sjbProject.repository.HotelRepository;
import com.sjb.sjbProject.repository.RoomRepository;

import jakarta.persistence.EntityManager;

@Service
public class SearchService {
	
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private RoomRepository roomRepository;
	 @Autowired
	 private EntityManager entityManager;
	
	//國家搜尋
	public List<Hotel> searchHotelsByCountry(String country) {
        return hotelRepository.findByCountryContaining(country);
    }
	
	//城市搜尋
	public List<Hotel> searchHotelsByCity(String city) {
	    return hotelRepository.findByCityName(city);
	}
	//城市、人數、上架下架日期
	public List<Room> searchRoomsByCityAndCapacityAndDateRange
	(String city, int requiredCapacity, Date startDate, Date endDate) 
	{
	    return roomRepository.findRoomsByCityAndCapacityAndDateRange
	    		(city, requiredCapacity, startDate, endDate);
	}
	//1022前台所需
	public Room getRoomById(String roomId) {
	   
	    return roomRepository.findById(roomId).orElse(null);
	}
	
	
	public List<Room> findAllRoom(Integer hotelID) {
		return roomRepository.findAllRoom(hotelID);
	}
	//庭安
			//顯示所有酒店查詢
	public List<Hotel> findAllHotel() {
        return hotelRepository.findAll();
    }
	public Hotel findHotelById(int hotelID) {
		return hotelRepository.findByHotelID(hotelID);
	}
	public List<Hotel> findHotelsByDateRange
	(String city, int requiredCapacity, Date startDate, Date endDate) 
	{
        return hotelRepository.findHotelsByDateRangeAndCriteria(city, requiredCapacity, startDate, endDate);
	}
 
	public Room findRoomByProductID(String productID) {
        return roomRepository.findByProductID(productID);
    }

    //1108處理hotelDetail
    public Hotel getHotelWithDetail(int hotelId) {
        Hotel hotel = entityManager.find(Hotel.class, hotelId);
        if (hotel != null) {
            // 触发加载 hotelDetail
            HotelDetail hotelDetail = hotel.getHotelDetail();
            hotel.setHotelDetail(hotelDetail);
            return hotel;
        } else {
            return null; // 可能需要处理未找到酒店的情况
        }
    }
}
