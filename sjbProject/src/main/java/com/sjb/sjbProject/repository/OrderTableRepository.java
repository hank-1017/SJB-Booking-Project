package com.sjb.sjbProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sjb.sjbProject.bean.OrderTable;

public interface OrderTableRepository extends JpaRepository<OrderTable, String> {
	  List<OrderTable> findByCustomerLoginID(Integer customerID);
	  
	  List<OrderTable> findByCustomerLoginIDOrderByOrderDetailsDetailIDDesc(Integer customerID);
	  
	  
	  //1104
	  @Query("SELECT o FROM OrderTable o JOIN o.orderDetails od ORDER BY od.checkInTime")
	  List<OrderTable> findAllOrderByCheckInTime();

	  @Query("SELECT o FROM OrderTable o JOIN o.rooms r WHERE r.hotelID = :hotelID")
	    List<OrderTable> findOrdersByHotelID(int hotelID);
}
