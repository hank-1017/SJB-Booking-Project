package com.sjb.sjbProject.repository;

import com.sjb.sjbProject.bean.HotelLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sjb.sjbProject.bean.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query(value="from Customer where customerName = :n")
	public Customer findCustomerByCustomerName(@Param("n") String name);

	@Query("SELECT c FROM Customer c WHERE c.loginID = :loginID")
	Optional<Customer> findByLoginID(@Param("loginID")Integer loginID);

	@Query(value="FROM Customer WHERE loginID = :loginID")
	Customer findByID(@Param("loginID") Integer loginID);
	
	Customer findByEmail(String email);
	
	Customer findByCustomerName(String customerName);

	@Query(value="FROM Customer WHERE googleID = :googleID")
	Customer findByGoogleID(@Param("googleID") String googleID);

	@Modifying
	@Query(value="UPDATE Customer h SET h.googleID = NULL WHERE h.loginID = :loginID")
	void clearGoogleID(@Param("loginID") String loginID);

	@Modifying
	@Query(value="UPDATE Customer SET googleID = :newGoogleID WHERE loginID = :loginID")
	void bindGoogleID(@Param("loginID") String loginID, @Param("newGoogleID") String newGoogleID);
		
	
}
