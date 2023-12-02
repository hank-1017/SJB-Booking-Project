package com.sjb.sjbProject.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity

@Table(name = "HotelDetail")
public class HotelDetail {
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "hotel"))
    @Id
    @Column(name = "hotelId")
    @GeneratedValue(generator = "generator")
    private Integer hotelId;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
//    @JsonIgnore
    private Hotel hotel;
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    private String mapURL;

    private String GUInumber;

    private String businessName;

    private Integer openYear;
    @Column(name = "cleaningService")
    private Boolean cleaningService = false;

    @Column(name = "expressCheckin")
    private Boolean expressCheckin = false;

    @Column(name = "counter24hr")
    private Boolean counter24hr = false;

    @Column(name = "freeWiFi")
    private Boolean freeWiFi = false;

    @Column(name = "roomCard")
    private Boolean roomCard = false;

    @Column(name = "noSmoking")
    private Boolean noSmoking = false;

    @Column(name = "petFriendly")
    private Boolean petFriendly = false;

    @Column(name = "petDeposit")
    private Integer petDeposit;

    @Column(name = "petCleaningFee")
    private Integer petCleaningFee;

    @Column(name = "reservationNotice")
    private String reservationNotice;

    public Integer getPetDeposit() {
        return petDeposit;
    }

    public void setPetDeposit(Integer petDeposit) {
        this.petDeposit = petDeposit;
    }

    public Integer getPetCleaningFee() {
        return petCleaningFee;
    }

    public void setPetCleaningFee(Integer petCleaningFee) {
        this.petCleaningFee = petCleaningFee;
    }

    public String getReservationNotice() {
        return reservationNotice;
    }

    public void setReservationNotice(String reservationNotice) {
        this.reservationNotice = reservationNotice;
    }

    public Boolean getExpressCheckin() {
        return expressCheckin;
    }

    public void setExpressCheckin(Boolean expressCheckin) {
        this.expressCheckin = expressCheckin;
    }

    public Boolean getCounter24hr() {
        return counter24hr;
    }

    public void setCounter24hr(Boolean counter24hr) {
        this.counter24hr = counter24hr;
    }

    public Boolean getFreeWiFi() {
        return freeWiFi;
    }

    public void setFreeWiFi(Boolean freeWiFi) {
        this.freeWiFi = freeWiFi;
    }

    public Boolean getRoomCard() {
        return roomCard;
    }

    public void setRoomCard(Boolean roomCard) {
        this.roomCard = roomCard;
    }

    public Boolean getNoSmoking() {
        return noSmoking;
    }

    public void setNoSmoking(Boolean noSmoking) {
        this.noSmoking = noSmoking;
    }

    public Boolean getPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(Boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public Boolean getCleaningService() {
        return cleaningService;
    }

    public void setCleaningService(Boolean cleaningService) {
        this.cleaningService = cleaningService;
    }

    public HotelDetail() {

    }


    public HotelDetail(String mapURL, String GUInumber, String businessName, Integer openYear) {
        this.mapURL = mapURL;
        this.GUInumber = GUInumber;
        this.businessName = businessName;
        this.openYear = openYear;
    }

    public String getGUInumber() {
		return GUInumber;
	}

	public void setGUInumber(String GUInumber) {
		 this.GUInumber = GUInumber;
	}

	public String getMapURL() {
        return mapURL;
    }

    public void setMapURL(String mapURL) {
        this.mapURL = mapURL;
    }



    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getOpenYear() {
        return openYear;
    }

    public void setOpenYear(Integer openYear) {
        this.openYear = openYear;
    }

}