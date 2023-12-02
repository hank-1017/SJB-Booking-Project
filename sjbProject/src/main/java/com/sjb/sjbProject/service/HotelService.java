package com.sjb.sjbProject.service;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.HotelDetail;
import com.sjb.sjbProject.bean.HotelLogin;
import com.sjb.sjbProject.repository.HotelDetailRepository;
import com.sjb.sjbProject.repository.HotelLoginRepository;
import com.sjb.sjbProject.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HotelService {
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private HotelLoginRepository hotelloginRepository;
	@Autowired
	private HotelDetailRepository hotelDetailRepository;
	public List<Hotel> findByLoginid(Integer loginid) {
		List<Hotel> hotels = hotelRepository.findByLoginid(loginid);
		return hotels;
	}

	public Hotel findByHotelId(int hotelID) {
		Optional<Hotel> optional = hotelRepository.findById(hotelID);
		if (optional.isPresent()) {
			Hotel hotel = optional.get();
			return hotel;
		} else {
			return null;
		}

	}

	public Hotel intserHotel(Integer loginId,Hotel hotel, HotelDetail hotelDetail) {
		Optional<HotelLogin> optional = hotelloginRepository.findById(loginId);
		if (optional.isPresent()) {
			HotelLogin resultLogin = optional.get();
			Set<Hotel> hotels = new HashSet<>();
			hotels.add(hotel);
			resultLogin.setHotels(hotels);
			hotel.setHotelLogin(resultLogin);
			hotel.setHotelDetail(hotelDetail);
			hotelDetail.setHotel(hotel);
			hotelRepository.save(hotel);
			return hotel;
		} else {
			return null;
		}

	}

	@Transactional
	public Hotel updateHotel(Integer hotelID, Hotel updatehotel) {
		Optional<Hotel> optional = hotelRepository.findById(hotelID);
		System.out.println("open");
		if (optional.isPresent()) {
			System.out.println("open");
			Hotel hotel = optional.get();
			hotel.setHotelName(updatehotel.getHotelName());
			hotel.setHotelType(updatehotel.getHotelType());
			hotel.setHotelStar(updatehotel.getHotelStar());
			hotel.setTel(updatehotel.getTel());
			hotel.setCountry(updatehotel.getCountry());
			hotel.setCity(updatehotel.getCity());
			hotel.setRegion(updatehotel.getRegion());
			hotel.setPostalCode(updatehotel.getPostalCode());
			hotel.setStreet(updatehotel.getStreet());
			hotel.setState(updatehotel.getState());
			System.out.println("open");
			return hotel;
		} else {
			System.out.println("錯誤");
			return null;
		}
	}

	@Transactional
	public HotelDetail updateHotelDetail(Integer hotelID, HotelDetail updatehotelDetail) {
		Optional<Hotel> optional = hotelRepository.findById(hotelID);
		if (optional.isPresent()) {
			Hotel hotel = optional.get();
			HotelDetail hotelDetail=hotel.getHotelDetail();
			hotelDetail.setGUInumber(updatehotelDetail.getGUInumber());
			hotelDetail.setBusinessName(updatehotelDetail.getBusinessName());
			hotelDetail.setOpenYear(updatehotelDetail.getOpenYear());
			return hotelDetail;
		} else {
			return null;
		}
	}
	public void deleteById(Integer hotelID) {
		hotelRepository.deleteById(hotelID);
	}


	public HotelDetail findHotelDetailByHotelId(int hotelID) {
		Optional<HotelDetail> optional = hotelDetailRepository.findById(hotelID);
		if (optional.isPresent()) {
			HotelDetail hotelDetail = optional.get();
			return hotelDetail;
		} else {
			return null;
		}

	}

	public List<Hotel> findAllHotel(){
		return hotelRepository.findAll();
	}

}