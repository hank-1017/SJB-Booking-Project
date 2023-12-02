package com.sjb.sjbProject.bean;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailID")
    private Integer detailID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    @JsonIgnore
    private OrderTable order;

    @Column(name = "productID")
    private String productID;

    @Column(name = "uniPrice")
    private String uniPrice;
    
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "payment")
    private String payment;

    @Column(name = "numPeople")
    private Integer numPeople;

    @Column(name = "checkInTime")
    private Date checkInTime;

    @Column(name = "checkOutTime")
    private Date checkOutTime;

    @Column(name = "evaluation")
    private String evaluation;

    @Column(name = "comment")
    private String comment;
    
    @Column(name = "orderName")
    private String orderName;
    
    @Column(name = "orderPhone")
    private String orderPhone;

    @Override
    public int hashCode() {
        return Objects.hash(detailID);
    }
}