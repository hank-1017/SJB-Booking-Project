package com.sjb.sjbProject.service;

import com.sjb.sjbProject.bean.RoomPicture;
import com.sjb.sjbProject.repository.RoomPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomPictureService {

    @Autowired
    private RoomPictureRepository roomPictureRepo;

    public List<RoomPicture> findAllRoomPictureByProductID(String productID){
        return roomPictureRepo.findAllByProductID(productID);
    }

    public RoomPicture findByimgID(Integer imgID){
        return roomPictureRepo.findByimgID(imgID);
    }

    public Integer deleteByimgID(Integer imgID){
        return  roomPictureRepo.deleteByimgID(imgID);
    }
}
