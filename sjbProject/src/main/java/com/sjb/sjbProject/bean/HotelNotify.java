package com.sjb.sjbProject.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Table(name = "HotelNotification ")
public class HotelNotify {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="hotelNoticeID")
    private int hotelNoticeID;

    @Column(name="hotelID")
    private int hotelID;

    @Column(name="noticeTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String noticeTime;

    @Column(name="content")
    private String content;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "hotelID",referencedColumnName = "hotelID",insertable = false, updatable = false)
    private Hotel hotel;

    public HotelNotify() {
    }
    public HotelNotify(int hotelID, String noticeTime, String content, String status) {
        this.hotelID = hotelID;
        this.noticeTime = noticeTime;
        this.content = content;
        this.status = status;
    }
}
