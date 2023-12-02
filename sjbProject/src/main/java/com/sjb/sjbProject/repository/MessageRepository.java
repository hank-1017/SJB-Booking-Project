package com.sjb.sjbProject.repository;

import com.sjb.sjbProject.bean.HotelNotify;
import com.sjb.sjbProject.bean.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "from Message where hotelID = :hotelID")
    List<Message> findAllMessageForHotel(@Param(value = "hotelID") Integer hotelID);

    @Query(value = "from Message where loginID = :loginID")
    List<Message> findAllMessageForCustomer(@Param(value = "loginID") Integer loginID);

    @Query("from Message where hotelID = :hotelID and loginID = :loginID")
    List<Message> findAllMessage(@Param(value = "hotelID") Integer hotelID,@Param(value = "loginID") Integer loginID);

    @Query("SELECT DISTINCT m.loginID FROM Message m WHERE m.hotelID = :hotelID")
    List<Integer> findDistinctLoginIDsByHotelID(@Param("hotelID") Integer hotelID);
    @Query("SELECT DISTINCT m.hotelID FROM Message m WHERE m.loginID = :loginID")
    List<Integer> findDistinctLoginIDsBycustomerLoginID(@Param("loginID") Integer loginID);


}
