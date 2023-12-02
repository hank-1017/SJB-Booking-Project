package com.sjb.sjbProject.service;

import java.util.HashSet;
import java.util.Set;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.HotelDetail;
import com.sjb.sjbProject.bean.HotelLogin;
import com.sjb.sjbProject.repository.HotelLoginRepository;
import org.springframework.web.bind.annotation.RequestParam;

import static com.sjb.sjbProject.config.JwtUtil.parseJWT;

@Service
public class HotelLoginService {
    @Autowired
    private HotelLoginRepository hotelloginRepository;
    @Autowired
    private PasswordEncoder pwdEncoder;

    public void insert(HotelLogin hotelLogin, Hotel hotel, HotelDetail hotelDetail) {
        String encodedPwd = pwdEncoder.encode(hotelLogin.getPassword());
        hotelLogin.setPassword(encodedPwd);
        System.out.println(hotelLogin.getEmail());
        Set<Hotel> hotels = new HashSet<>();
        hotels.add(hotel);
        hotelLogin.setHotels(hotels);
        hotel.setHotelLogin(hotelLogin);
        hotel.setHotelDetail(hotelDetail);
        hotelDetail.setHotel(hotel);
        hotelloginRepository.save(hotelLogin);
    }


    //驗證JWT，回傳loginID
    public String checkToken(String token) {
        try {
            Claims claims = parseJWT(token);

            if (claims != null) {
                // 驗證成功
                System.out.println("JWT驗證成功");
                System.out.println("Subject: " + claims.getSubject());
                System.out.println("創建日: " + claims.getIssuedAt());
                System.out.println("到期日: " + claims.getExpiration());
                return claims.getSubject();
            } else {
                // 驗證失敗
                System.out.println("JWT驗證失敗");
                return null;
            }
        } catch (Exception e) {
            System.out.println("JWT驗證失敗");
            return null;
        }
    }

    public boolean checkIfEmailExist(String email) {//檢查email是否存在
        HotelLogin dbHotel = hotelloginRepository.findByEmail(email);
        return dbHotel != null;
    }


    public Boolean resetPwd(String email, String newPwd) {//更新密碼
        HotelLogin dbHotel = hotelloginRepository.findByEmail(email);
        if (dbHotel != null) {
            String encodedPwd = pwdEncoder.encode(newPwd);
            dbHotel.setPassword(encodedPwd);
            hotelloginRepository.save(dbHotel);
            System.out.println("更新後密碼: " + dbHotel.getPassword());
            return true;
        }
        return false;
    }

    public HotelLogin checkLogin(String email, String inputPwd) {
        HotelLogin dbHotel = hotelloginRepository.findByEmail(email);
        //比對加密
        if (dbHotel != null) {
            if (pwdEncoder.matches(inputPwd, dbHotel.getPassword())) {
                return dbHotel;
            }
        }
        return null;
    }

    public HotelLogin getLoginData(String loginID) {
        return hotelloginRepository.findById(loginID);  //取得登入資料

    }
    public HotelLogin getLoginDataByEmail(String email) {
        return hotelloginRepository.findByEmail(email);  //取得登入資料

    }

    //使用googleID 取得HotelLogin
    public HotelLogin oauth2CheckLogin(String googleID) {//google登入
        return hotelloginRepository.findByGoogleID(googleID);
    }

    //是否有googleID
    public boolean checkGoogleIdExistByEmail(String email) {
        HotelLogin dbHotel = hotelloginRepository.findByEmail(email);
        return dbHotel.getGoogleID() != null;
    }

    @Transactional //清除googleID
    public void clearGoogleID(String loginID) {//清除googleID
        hotelloginRepository.clearGoogleID(loginID);
    }

    @Transactional //新增googleID
    public void bindGoogleID(String loginID, String newGoogleID) {
        hotelloginRepository.bindGoogleID(loginID, newGoogleID);
    }

    @Transactional //更新頭像
    public void updateHeadshot(String loginID, byte[] headshot) {
    	hotelloginRepository.updateHeadshot(loginID, headshot);
    }

    @Transactional //更新名稱
    public void updateLoginName(String loginID, String loginName) {
    	hotelloginRepository.updateLoginName(loginID, loginName);
    }

}
