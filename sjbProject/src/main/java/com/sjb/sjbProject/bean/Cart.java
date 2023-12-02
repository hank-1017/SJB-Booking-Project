package com.sjb.sjbProject.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "cartID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartID;

    @Column(name = "loginID",insertable = false,updatable = false)
//    @Column(name = "loginID")
    private Integer loginID;

    @Column(name = "hotelID")
    private Integer hotelID;

    @Column(name = "productID")
    private String productID;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "checkInDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @Column(name = "checkOutDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "loginID",nullable = false)
    @JsonIgnore
    private Customer customer;

}
