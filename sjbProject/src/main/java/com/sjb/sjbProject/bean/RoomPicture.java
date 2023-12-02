package com.sjb.sjbProject.bean;

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
@Table(name = "RoomPicture")
public class RoomPicture {

	@Id
	@Column(name = "imgID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer imgID;

	@Column(name = "productID")
	private String productID;

	@Column(name = "imagePath")
	private byte[] imagePath;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "productID",referencedColumnName = "productID", insertable = false, updatable = false)
	private Room room;
}
