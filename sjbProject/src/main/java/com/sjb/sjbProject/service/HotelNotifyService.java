package com.sjb.sjbProject.service;

import com.sjb.sjbProject.bean.HotelNotify;
import com.sjb.sjbProject.repository.HotelNotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelNotifyService {

    @Autowired
    private HotelNotifyRepository hotelNotifyRepository;

    public Boolean insertHotelNotify(HotelNotify hotelNotify){
        HotelNotify rs = hotelNotifyRepository.save(hotelNotify);
        return rs != null;
    }

    public void updateHotelNotify(HotelNotify hotelNotify){
        hotelNotifyRepository.save(hotelNotify);
    }

    public Boolean deleteHotelNotify(Integer hotelNoticeID){
        hotelNotifyRepository.deleteById(hotelNoticeID);
        return true;
    }

    public HotelNotify findHotelNotifyById(Integer hotelNoticeID){
        return hotelNotifyRepository.findById(hotelNoticeID).orElse(null);
    }

    public List<HotelNotify> findAllNotify(Integer hotelID) {
        return hotelNotifyRepository.findAllNotify(hotelID);
    }

}
