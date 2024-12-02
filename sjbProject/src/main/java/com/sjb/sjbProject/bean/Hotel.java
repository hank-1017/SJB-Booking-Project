package com.sjb.sjbProject.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "hotel")
@JsonIgnoreProperties({"hotelLogin","hotelDetail","hotelNotifies","Message"})
//阻擋特定欄位序列化
public class Hotel {
	@Id
	@Column(name = "hotelID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer hotelID;
	@Column(name = "loginid", insertable = false, updatable = false)
	private Integer loginid;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loginID")
	private HotelLogin hotelLogin;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL)
	private HotelDetail hotelDetail;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL)
	private Set<Room> rooms;


	//1107新增
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL)
	private Set<HotelNotify> hotelNotifies;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL)
	private Set<Message> messages;

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<HotelNotify> getHotelNotifies() {
		return hotelNotifies;
	}

	public void setHotelNotifies(Set<HotelNotify> hotelNotifies) {
		this.hotelNotifies = hotelNotifies;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		rooms = rooms;
	}

	private String hotelName;
	private String hotelType;
	private String hotelStar;
	private String tel;
	private String country;
	private String city;
	private String region;
	private String street;
	private String postalCode;
	private Boolean state;


	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Hotel() {
	}


	public Hotel(String hotelName, String hotelType, String hotelStar, String tel, String country, String city,
				 String region, String street, String postalCode, Boolean state) {
		this.hotelName = hotelName;
		this.hotelType = hotelType;
		this.hotelStar = hotelStar;
		this.tel = tel;
		this.country = country;
		this.city = city;
		this.region = region;
		this.street = street;
		this.postalCode = postalCode;
		this.state = state;
	}

	public HotelLogin getHotelLogin() {
		return hotelLogin;
	}

	public void setHotelLogin(HotelLogin hotelLogin) {
		this.hotelLogin = hotelLogin;
	}

	public HotelDetail getHotelDetail() {
		return hotelDetail;
	}

	public void setHotelDetail(HotelDetail hotelDetail) {
		this.hotelDetail = hotelDetail;
	}

	public Integer getHotelID() {
		return hotelID;
	}

	public void setHotelID(Integer hotelID) {
		this.hotelID = hotelID;
	}

	public Integer getLoginid() {
		return loginid;
	}

	public void setLoginid(Integer loginid) {
		this.loginid = loginid;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getHotelStar() {
		return hotelStar;
	}

	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}