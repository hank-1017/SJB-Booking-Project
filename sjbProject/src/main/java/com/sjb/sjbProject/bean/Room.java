package com.sjb.sjbProject.bean;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "Room")
public class Room {

	@Column(name = "hotelID")
	private Integer hotelID;

	@Id
	@Column(name = "productID")
	private String productID;

	@Column(name = "productName")
	private String productName;

	@Column(name = "productType")
	private String productType;

	@Column(name = "price")
	private Integer price;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "dateStart")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;

	@Column(name = "dateEnd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateEnd;

	@Column(name = "enableSwitch")
	private Boolean enableSwitch;

	@Column(name = "capacity")
	private Integer capacity;

	@Column(name = "productDescription")
	private String productDescription;

	@Column(name = "score")
	private Integer score;

	@Column(name = "checkInTime")
	private String checkInTime;

	@Column(name = "checkOutTime")
	private String checkOutTime;

	@Column(name = "childExtraBed")
	private Boolean childExtraBed = false;

	@Column(name = "childrenPrice")
	private Integer childrenPrice;

	@Column(name = "age")
	private Integer age;

	@Column(name = "privateBathroom")
	private Boolean privateBathroom = false;

	@Column(name = "showerRoom")
	private Boolean showerRoom = false;

	@Column(name = "discount")
	private Double discount;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "room")
	private List<RoomPicture> roomPictures;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "hotelID",referencedColumnName = "hotelID",insertable = false, updatable = false)
	private Hotel hotel;

	//1104更新
	@ManyToMany(mappedBy = "rooms", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<OrderTable> orders;

	@Override
	public int hashCode() {
		return Objects.hash(productID); // 根据对象的某个唯一属性来计算哈希码
	}

}
