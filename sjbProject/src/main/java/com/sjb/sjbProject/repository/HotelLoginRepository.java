package com.sjb.sjbProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjb.sjbProject.bean.HotelLogin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelLoginRepository extends JpaRepository<HotelLogin, Integer> {
    @Query(value="FROM HotelLogin WHERE email = :email")
    HotelLogin findByEmail(@Param("email") String email);

    @Query(value="FROM HotelLogin WHERE loginID = :loginID")
    HotelLogin findById(@Param("loginID") String loginID);

    @Query(value="FROM HotelLogin WHERE googleID = :googleID")
    HotelLogin findByGoogleID(@Param("googleID") String googleID);

    @Modifying
    @Query(value="UPDATE HotelLogin h SET h.googleID = NULL WHERE h.loginID = :loginID")
    void clearGoogleID(@Param("loginID") String loginID);

    @Modifying
    @Query(value="UPDATE HotelLogin SET googleID = :newGoogleID WHERE loginID = :loginID")
    void bindGoogleID(@Param("loginID") String loginID, @Param("newGoogleID") String newGoogleID);

    @Modifying
    @Query(value="UPDATE HotelLogin SET headshot = :headshot WHERE loginID = :loginID")
    void updateHeadshot(String loginID, byte[] headshot);
    @Modifying
    @Query(value="UPDATE HotelLogin SET loginName = :loginName WHERE loginID = :loginID")
    void updateLoginName(String loginID, String loginName);

}
