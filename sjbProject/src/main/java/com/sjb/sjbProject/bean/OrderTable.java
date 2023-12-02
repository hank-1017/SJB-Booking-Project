package com.sjb.sjbProject.bean;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderTable {
    @Id
    @Column(name = "orderID", nullable = false)
    private String orderID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loginID", nullable = false)
    @JsonIgnore
    private Customer customer;

    @Column(name = "orderStatus", nullable = false, length = 40)
    private String orderStatus;

    @Column(name = "orderTime", nullable = false, length = 40)
    private String orderTime;

    @Column(name = "updateTime", nullable = false, length = 40)
    private String updateTime;
    
  
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;



    //1104更新
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "OrderAndRoom",
            joinColumns = @JoinColumn(name = "orderID", referencedColumnName = "orderID"),
            inverseJoinColumns = @JoinColumn(name = "productID", referencedColumnName = "productID"))
    private Set<Room> rooms = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(orderID); // 根据对象的某个唯一属性来计算哈希码
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
  

}