package com.sjb.sjbProject.bean;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "HotelLogin")
public class HotelLogin {
    @Id
    @Column(name = "loginID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loginID;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotelLogin", cascade = CascadeType.ALL)
    private Set<Hotel> hotels = new HashSet<>();

    public Set<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(Set<Hotel> hotels) {
        this.hotels = hotels;
    }

    private String email;
    private String password;
    private String loginName;
    private byte[]  headshot;
    private String googleID;
    private String LineID;

    public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public HotelLogin() {
    }

    //10-24更新
    public HotelLogin(String email, String password,String loginName,byte[] headshot,String googleID,String LineID) {
        this.email = email;
        this.password = password;
        this.loginName=loginName;
        this.headshot=headshot;
        this.googleID=googleID;
        this.LineID=LineID;
    }

    public int getLoginID() {
        return loginID;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
