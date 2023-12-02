package com.sjb.sjbProject.repository;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.HotelDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelDetailRepository extends JpaRepository<HotelDetail, Integer> {
}
