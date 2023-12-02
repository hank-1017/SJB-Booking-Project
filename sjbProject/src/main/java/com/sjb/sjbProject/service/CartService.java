package com.sjb.sjbProject.service;

import com.sjb.sjbProject.bean.Cart;
import com.sjb.sjbProject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepo;

    public Cart insertCart (Cart cart){
        return cartRepo.save(cart);
    }

    public List<Cart> findAllByLoginID(Integer LoginID){
        return cartRepo.findAllByLoginID(LoginID);
    }

    public Cart findByCartID(Integer cartID){
        return cartRepo.findByCartID(cartID);
    }
    public Cart findByLoginIDAndProductID(Integer loginID, String productID){
        return  cartRepo.findByLoginIDAndProductID(loginID,productID);
    }

    public Integer deleteByCartID(Integer cartID){
        return cartRepo.deleteByCartID(cartID);
    }
}
