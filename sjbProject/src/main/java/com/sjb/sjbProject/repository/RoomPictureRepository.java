package com.sjb.sjbProject.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sjb.sjbProject.bean.RoomPicture;

import java.util.List;

public interface RoomPictureRepository extends JpaRepository<RoomPicture, Integer> {
    List<RoomPicture> findAllByProductID(String productID);

    RoomPicture findByimgID(Integer imgID);

    @Transactional
    Integer deleteByimgID(Integer imgID);
}
