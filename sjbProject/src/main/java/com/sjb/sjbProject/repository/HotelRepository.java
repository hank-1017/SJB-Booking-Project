package com.sjb.sjbProject.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sjb.sjbProject.bean.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
	
	@Query("SELECT h FROM Hotel h WHERE h.loginid = :loginid")
    List<Hotel> findByLoginid(@Param("loginid") Integer loginid);

    @Query("SELECT h FROM Hotel h WHERE h.country LIKE %:country%")
    List<Hotel> findByCountryContaining(@Param("country") String country);


    //1030查詢
    @Query("SELECT h FROM Hotel h WHERE h.city LIKE %:cityName%")
    List<Hotel> findByCityName(@Param("cityName") String cityName);

    //1031
    Hotel findByHotelID(int hotelID);

    //1031搜尋符合房間日期的HOTEL
    @Query("SELECT DISTINCT h FROM Hotel h " +
            "JOIN h.rooms r " +
            "WHERE r.dateStart <= :endDate " +
            "AND r.dateEnd >= :startDate " +
            "AND (h.city LIKE %:city%) " +
            "AND r.capacity >= :requiredCapacity")
    List<Hotel> findHotelsByDateRangeAndCriteria(
            @Param("city") String city,
            @Param("requiredCapacity") int requiredCapacity,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );


}
