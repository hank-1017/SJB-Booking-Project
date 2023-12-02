package com.sjb.sjbProject.controller;


import com.sjb.sjbProject.bean.HotelNotify;
import com.sjb.sjbProject.bean.Room;
import com.sjb.sjbProject.service.HotelNotifyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true") // 跨域請求設定
public class HotelNotifyController {

    @Autowired
    private HotelNotifyService hotelNotifyService;

    @GetMapping("/getAllNotify")
    public List<HotelNotify> getHotelNotify(HttpSession httpSession) {

        Integer hotelID = (Integer) httpSession.getAttribute("hotelID");
        return hotelNotifyService.findAllNotify(hotelID);
    }

    @GetMapping("/getTop4Notify")
    public List<HotelNotify> getTop3Notify(HttpSession httpSession) {

        Integer hotelID = (Integer) httpSession.getAttribute("hotelID");

        List<HotelNotify> notifies = hotelNotifyService.findAllNotify(hotelID);

        // 倒序排序
        notifies.sort((n1, n2) -> n2.getNoticeTime().compareTo(n1.getNoticeTime()));

        // 取前 4 筆
        return notifies.subList(0, Math.min(4, notifies.size()));

    }
    @GetMapping("/changeNotifyState")
    public String changeNotifyState(@RequestParam(name = "hotelNoticeID") Integer hotelNoticeID) {

        HotelNotify res= hotelNotifyService.findHotelNotifyById(hotelNoticeID);
        res.setStatus("已讀");
        hotelNotifyService.updateHotelNotify(res);
        return "success";
    }

    @GetMapping("/deleteNotify")
    public String deleteNotify(@RequestParam(name = "hotelNoticeID") Integer hotelNoticeID) {

        hotelNotifyService.deleteHotelNotify(hotelNoticeID);
        return "success";
    }




}
