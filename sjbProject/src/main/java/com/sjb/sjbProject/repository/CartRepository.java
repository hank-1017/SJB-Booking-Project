package com.sjb.sjbProject.repository;

import com.sjb.sjbProject.bean.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByLoginID(Integer LoginID);

    Cart findByCartID(Integer cartID);

    Cart findByLoginIDAndProductID(Integer loginID, String productID);

    @Transactional
    Integer deleteByCartID(Integer cartID);

}
