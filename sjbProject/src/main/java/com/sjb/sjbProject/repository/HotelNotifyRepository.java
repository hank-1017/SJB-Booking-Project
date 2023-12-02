package com.sjb.sjbProject.repository;

import com.sjb.sjbProject.bean.HotelNotify;
import com.sjb.sjbProject.bean.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelNotifyRepository extends JpaRepository<HotelNotify, Integer> {

    @Query(value = "from HotelNotify where hotelID = :hotelID")
    List<HotelNotify> findAllNotify(@Param(value = "hotelID") Integer hotelID);
}
