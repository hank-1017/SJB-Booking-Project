package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.HotelDetail;
import com.sjb.sjbProject.bean.HotelNotify;
import com.sjb.sjbProject.service.HotelLoginService;
import com.sjb.sjbProject.service.HotelNotifyService;
import com.sjb.sjbProject.service.HotelService;
import com.sjb.sjbProject.service.MailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true") // 跨域請求設定
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelNotifyService hotelNotifyService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    // 10/19  vue查詢飯店頁
    @PostMapping("/getAllHotel")
    public ResponseEntity<List<Hotel>> getAllHotel(HttpSession httpSession) {
        Object loginIdObj = httpSession.getAttribute("loginId");
        if (loginIdObj != null) {
            Integer loginId = (Integer) loginIdObj;
            List<Hotel> hotels = hotelService.findByLoginid(loginId);
            return ResponseEntity.ok(hotels);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    //新增飯店頁
    @PostMapping("/add")
    public String postHotel(@RequestParam("hotelName") String hotelName,
                            @RequestParam("hotelType") String hotelType,
                            @RequestParam("hotelStar") String hotelStar,
                            @RequestParam("tel") String tel,
                            @RequestParam("country") String country,
                            @RequestParam("city") String city,
                            @RequestParam("region") String region,
                            @RequestParam("street") String street,
                            @RequestParam("postalCode") String postalCode,
                            @RequestParam("GUInumber") String GUInumber,
                            @RequestParam("businessName") String businessName,
                            @RequestParam("openYear") String openYear, HttpSession httpSession) {
        String mapURL = "0";
        String state = "false";
        Object hotelAttribute = httpSession.getAttribute("loginId");
        Integer loginId = (Integer) hotelAttribute;
        Hotel hotel = new Hotel(hotelName, hotelType, hotelStar, tel, country, city, region, street, postalCode, Boolean.parseBoolean(state));
        HotelDetail hotelDetail = new HotelDetail(mapURL, GUInumber, businessName, Integer.parseInt(openYear));
        hotelService.intserHotel(loginId, hotel, hotelDetail);
        return "Y";
    }


    //10/19 vue飯店詳細資料查詢頁
    @GetMapping("/findHotel")
    public ResponseEntity<Hotel> findHotel(HttpSession httpSession) {
        Object hotelIDObj = httpSession.getAttribute("hotelID");
        if (hotelIDObj != null) {
            int hotelID = Integer.parseInt(hotelIDObj.toString());
            httpSession.setAttribute("hotelIDForGetHotel", hotelID);
            System.out.println("Set hotelIDForDetail: " + hotelID);
            Hotel hotel = hotelService.findByHotelId(hotelID);
            return ResponseEntity.ok(hotel);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Hotel>> getAll() {
        List<Hotel> hotels = hotelService.findAllHotel();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/getHotel")
    public ResponseEntity<Hotel> getHotel(HttpSession httpSession) {
        Object hotelIDForGetHotelObj = httpSession.getAttribute("hotelIDForGetHotel");
        if (hotelIDForGetHotelObj != null) {
            Integer hotelIDForGetHotel = Integer.valueOf(hotelIDForGetHotelObj.toString());
            System.out.println("Get hotelIDForDetail: " + hotelIDForGetHotel);
            Hotel hotel = hotelService.findByHotelId(hotelIDForGetHotel);

            return ResponseEntity.ok(hotel);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/getHotelName/{hotelID}")
    public String getHotelName(@PathVariable Integer hotelID) {
        Hotel hotel = hotelService.findByHotelId(hotelID);
        return hotel.getHotelName();
    }

    @GetMapping("/checkHotelExist/{hotelID}")
    public Boolean checkHotelExist(@PathVariable Integer hotelID) {
        Hotel hotel = hotelService.findByHotelId(hotelID);
        return hotel != null;
    }

    @GetMapping("/getHotelDetail")
    public ResponseEntity<HotelDetail> getHotelDetail(HttpSession httpSession) {
        Object hotelIDForGetHotelObj = httpSession.getAttribute("hotelIDForGetHotel");
        if (hotelIDForGetHotelObj != null) {
            Integer hotelIDForGetHotel = Integer.valueOf(hotelIDForGetHotelObj.toString());
            System.out.println("Get hotelIDForDetail: " + hotelIDForGetHotel);
            Hotel hotel = hotelService.findByHotelId(hotelIDForGetHotel);
            HotelDetail hotelDetail = hotel.getHotelDetail();
            System.out.println("Get GUInumber: " + hotelDetail.getGUInumber());

            return ResponseEntity.ok(hotelDetail);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    //更新飯店資料
    @PutMapping("/update")
    public String updateHotelById(
            @RequestParam("hotelName") String hotelName,
            @RequestParam("hotelType") String hotelType,
            @RequestParam("hotelStar") String hotelStar,
            @RequestParam("tel") String tel,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("region") String region,
            @RequestParam("street") String street,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("GUInumber") String GUInumber,
            @RequestParam("businessName") String businessName,
            @RequestParam("openYear") String openYear, HttpSession httpSession) {
        Object hotelIDObj = httpSession.getAttribute("hotelID");
        String mapURL = "0";
        String state = "false";
        if (hotelIDObj != null) {
            Integer hotelID = Integer.valueOf(hotelIDObj.toString());
            Hotel updatehotel = new Hotel(hotelName, hotelType, hotelStar, tel, country, city, region, street, postalCode, Boolean.parseBoolean(state));
            HotelDetail updatehotelDetail = new HotelDetail(mapURL, GUInumber, businessName, Integer.parseInt(openYear));
            hotelService.updateHotel(hotelID, updatehotel);
            hotelService.updateHotelDetail(hotelID, updatehotelDetail);
            System.out.println("更新成功");
            HotelNotify hotelNotify = new HotelNotify(hotelID, sdf.format(new Date()), "您的住宿基本資料已更新", "未讀");
            hotelNotifyService.insertHotelNotify(hotelNotify);
            return "Y";
        }
        System.out.println("更新失敗");
        return "N";
    }


    //  啟用/停用飯店
    @PutMapping("/checkState")
    public Boolean stopHotelById(@RequestParam("state") String state,
                                 HttpSession httpSession) {
        int hotelID = (int) httpSession.getAttribute("hotelID");
        Hotel updateHotel = hotelService.findByHotelId(hotelID);
        updateHotel.setState(Boolean.parseBoolean(state));
        hotelService.updateHotel(hotelID, updateHotel);
        //  通知飯店狀態已更新
        if (Boolean.parseBoolean(state)) {
            HotelNotify hotelNotify = new HotelNotify(hotelID, sdf.format(new Date()), "您的住宿已上線", "未讀");
            hotelNotifyService.insertHotelNotify(hotelNotify);
        } else {
            HotelNotify hotelNotify = new HotelNotify(hotelID, sdf.format(new Date()), "您的住宿已下線", "未讀");
            hotelNotifyService.insertHotelNotify(hotelNotify);
        }
        return Boolean.parseBoolean(state);
    }


    //  設定寵物押金清潔費
//    @PostMapping("/SetPetMoney")
//    public String SetPetMoney(@RequestParam("hotelID") Integer hotelID,
//                              @RequestParam("petDeposit") Integer petDeposit,
//                              @RequestParam("petCleaningFee") Integer petCleaningFee) {
//        HotelDetail updateHotelDetail = hotelService.findHotelDetailByHotelId(hotelID);
//        updateHotelDetail.setPetDeposit(petDeposit);
//        updateHotelDetail.setPetCleaningFee(petCleaningFee);
//        hotelService.updateHotelDetail(hotelID, updateHotelDetail);
//        return "Y";
//    }

    //切換飯店
    @GetMapping("/setHotelID")
    public String setHotelID(@RequestParam(name = "hotelID") Integer hotelID, HttpSession session) {

        session.setAttribute("hotelID", hotelID);
        System.out.println("sethotelID: " + hotelID);

        return "sethotelID" + hotelID;
    }


    @PutMapping("/updateHotelService")
    public String updateHotelService(
            @RequestParam("cleanService") boolean cleanService,
            @RequestParam("expressCheckin") boolean expressCheckin,
            @RequestParam("petFriendly") boolean petFriendly,
            @RequestParam("noSmoking") boolean noSmoking,
            @RequestParam("counter24hr") boolean counter24hr,
            @RequestParam("freeWiFi") boolean freeWiFi,
            @RequestParam("petDeposit") Integer petDeposit,
            @RequestParam("petCleaningFee") Integer petCleaningFee,
            @RequestParam("reservationNotice") String reservationNotice,
            @RequestParam("roomCard") boolean roomCard,
            HttpSession httpSession) {

        Object hotelIDObj = httpSession.getAttribute("hotelID");
        if (hotelIDObj != null) {
            int hotelID = Integer.parseInt(hotelIDObj.toString());
            HotelDetail updateHotelDetail = hotelService.findHotelDetailByHotelId(hotelID);
            updateHotelDetail.setReservationNotice(reservationNotice);
            updateHotelDetail.setPetDeposit(petDeposit);
            updateHotelDetail.setPetCleaningFee(petCleaningFee);
            updateHotelDetail.setExpressCheckin(expressCheckin);
            updateHotelDetail.setPetFriendly(petFriendly);
            updateHotelDetail.setNoSmoking(noSmoking);
            updateHotelDetail.setRoomCard(roomCard);
            updateHotelDetail.setFreeWiFi(freeWiFi);
            updateHotelDetail.setCounter24hr(counter24hr);
            updateHotelDetail.setCleaningService(cleanService);

            hotelService.updateHotelDetail(hotelID, updateHotelDetail);

            //  通知飯店服務已更新
            HotelNotify hotelNotify = new HotelNotify(hotelID, sdf.format(new Date()), "您的住宿服務已更新", "未讀");
            hotelNotifyService.insertHotelNotify(hotelNotify);

            return "Y";
        }
        return "N";
    }


}
