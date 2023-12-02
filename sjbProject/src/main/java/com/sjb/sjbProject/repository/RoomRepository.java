package com.sjb.sjbProject.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sjb.sjbProject.bean.Room;

import jakarta.transaction.Transactional;

public interface RoomRepository extends JpaRepository<Room, String> {

	@Query(value = "from Room where productID=:productID and hotelID=:hotelID")
	Room findRoomById(@Param(value = "productID") String productID, @Param(value = "hotelID") Integer hotelID);

	@Transactional
	@Modifying
	@Query("DELETE FROM Room WHERE productID=:productID and hotelID=:hotelID")
	Integer deleteRoomById(@Param(value = "productID") String productID, @Param(value = "hotelID") Integer hotelID);

	@Query(value = "from Room where hotelID = :hotelID")
	List<Room> findAllRoom(@Param(value = "hotelID") Integer hotelID);
	
	@Transactional
	Integer deleteByProductIDAndHotelID(String productID, Integer hotelID);

	//檢查時間範圍1025
	@Query("SELECT COUNT(r) FROM Room r WHERE r.productID = :productID " +
			"AND (r.dateStart <= :endDate AND r.dateEnd >= :startDate)")
	long countRoomsInDateRange(@Param("productID") String productID,
							   @Param("startDate") Date startDate,
							   @Param("endDate") Date endDate);
	//檢查房間數量1027
	@Query("SELECT r.quantity FROM Room r WHERE r.productID = :productID")
	int findAvailableQuantityByProductID(@Param("productID") String productID);

	//檢查房間飯店入住日期飯為1030
	@Query("SELECT r FROM Room r " +
			"INNER JOIN r.hotel h " +
			"WHERE h.city LIKE %:city% " +
			"AND r.capacity >= :requiredCapacity " +
			"AND r.dateStart <= :endDate " +
			"AND r.dateEnd >= :startDate")
	List<Room> findRoomsByCityAndCapacityAndDateRange(
			@Param("city") String city,
			@Param("requiredCapacity") int requiredCapacity,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate
	);
	
	Room findByProductID(String productID);


}
