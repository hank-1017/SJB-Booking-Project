package com.sjb.sjbProject.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
@Entity
@Table(name = "Customer")
public class Customer {

	
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "customerID")
//	private int customerID;

//	@PrimaryKeyJoinColumn
//	@OneToOne(fetch = FetchType.LAZY)
//	private CustomerLogin customerLogin;
	
//	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "customerLogin"))
//	@JoinColumn(name = "loginID")
	
	@Id
	@Column(name = "loginID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer loginID;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;

	@Column(name = "CustomerName")
	private String customerName;

	@Column(name = "sex")
	private String sex;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "phone")
	private String phone;

	@Lob
	@Column(name = "headshot")
	private byte[] headshot;

	@Column(name = "country")
	private String country;

	@Column(name = "city")
	private String city;

	@Column(name = "region")
	private String region;

	@Column(name = "street")
	private String street;

	@Column(name = "postalCode")
	private String postalCode;

	@Column(name = "googleID")
	private String googleID;

	@OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Cart> cart;

//	@OneToMany(mappedBy = "customer")
//	private Set<OrderTable> orders = new HashSet<>();


	public Customer() {
	}

	public Customer(String email, String password, String customerName, String sex, Date birthday, String phone, byte[] headshot, String country, String city, String region, String street, String postalCode) {
		this.email = email;
		this.password = password;
		this.customerName = customerName;
		this.sex = sex;
		this.birthday = birthday;
		this.phone = phone;
		this.headshot = headshot;
		this.country = country;
		this.city = city;
		this.region = region;
		this.street = street;
		this.postalCode = postalCode;
	}

	public Customer(String customerName, String sex, Date birthday, String phone, byte[] headshot,
					String country, String city, String region, String street, String postalCode) {
		this.customerName = customerName;
		this.sex = sex;
		this.birthday = birthday;
		this.phone = phone;
		this.headshot = headshot;
		this.country = country;
		this.city = city;
		this.region = region;
		this.street = street;
		this.postalCode = postalCode;
	}


//	public Customer(CustomerLogin customerLogin, String customerName) {
//		super();
//		this.customerLogin = customerLogin;
//		this.customerName = customerName;
//	}

	public int getLoginID() {
		return loginID;
	}

	public void setLoginID(int loginID) {
		this.loginID = loginID;
	}

//	public int getCustomerID() {
//		return customerID;
//	}
//
//	public void setCustomerID(int customerID) {
//		this.customerID = customerID;
//	}

//	public CustomerLogin getCustomerLogin() {
//		return customerLogin;
//	}
//
//	public void setCustomerLogin(CustomerLogin customerLogin) {
//		this.customerLogin = customerLogin;
//	}
	
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte[] getHeadshot() {
		return headshot;
	}

	public void setHeadshot(byte[] headshot) {
		this.headshot = headshot;
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

//	public Set<OrderTable> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(Set<OrderTable> orders) {
//		this.orders = orders;
//	}
}
