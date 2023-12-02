package com.sjb.sjbProject.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@Table(name = "Message ")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageID")
    private int messageID;

    @Column(name = "hotelID")
    private int hotelID;

    @Column(name = "loginID")
    private int loginID;

    @Column(name = "messageTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String messageTime;

    @Column(name = "content")
    private String content;



    @Column(name = "sender")
    private String sender;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "hotelID",referencedColumnName = "hotelID",insertable = false, updatable = false)
    private Hotel hotel;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "loginID",referencedColumnName = "loginID",insertable = false, updatable = false)
    private Customer customer;


    public Message() {
    }

    public Message(int hotelID, int loginID, String messageTime, String content, String status) {
        this.hotelID = hotelID;
        this.loginID = loginID;
        this.messageTime = messageTime;
        this.content = content;
        this.status = status;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getLoginID() {
        return loginID;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", hotelID=" + hotelID +
                ", loginID=" + loginID +
                ", messageTime='" + messageTime + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}