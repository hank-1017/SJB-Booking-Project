package com.sjb.sjbProject.service;

import java.io.IOException;
import java.util.Optional;

import com.sjb.sjbProject.bean.HotelLogin;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sjb.sjbProject.bean.Customer;
import com.sjb.sjbProject.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository cuRepo;

    @Autowired
    private PasswordEncoder pwdEncoder;

    public Customer AddCustomer(Customer cus) {
        System.out.println("into AddCustomer");
        System.out.println(cus.getPassword());

        String encodedPwd = pwdEncoder.encode(cus.getPassword());// 加密

        System.out.println("after encodedPwd");

        cus.setPassword(encodedPwd);
        return cuRepo.save(cus);
    }

    public Customer findById2(Integer id) {
        Optional<Customer> cuid = cuRepo.findById(id);

        if (cuid.isPresent()) {
            return cuid.get();
        }
        return null;
    }

    public Customer findCustomerByCustomerName(String name) {
//		 return cuRepo.findById(id);
        Customer customer = cuRepo.findCustomerByCustomerName(name);
        System.out.println(customer.getCustomerName());
        return customer;
    }

//	public Customer findById(Integer id) {
//		Optional<Customer> cid = cuRepo.findById(id);
//
//		if (cid.isPresent()) {
//			return cid.get();
//		}
//
//		return null;
//	}

    public Customer findById(Integer loginID) {

        return cuRepo.findByID(loginID);
    }

    public void uploadHeadshot(Integer customerId, byte[] headshotData) {
        Optional<Customer> optionalCustomer = cuRepo.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setHeadshot(headshotData);
            cuRepo.save(customer);
        } else {
            return;
        }
    }

    public void uploadHeadshot(Integer customerId, MultipartFile headshotFile) throws IOException {
        Optional<Customer> optionalCustomer = cuRepo.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (!headshotFile.isEmpty()) {
                // 讀取MultipartFile的二進制數據
                byte[] headshotData = headshotFile.getBytes();
                customer.setHeadshot(headshotData);
                cuRepo.save(customer);
            } else {
                // 如果MultipartFile是空的，可以處理錯誤或提供相應的錯誤訊息
                throw new IllegalArgumentException("Headshot file is empty.");
            }
        } else {
            // 如果找不到客戶，可以處理錯誤或提供相應的錯誤訊息
            throw new IllegalArgumentException("查無此ID: " + customerId);
        }
    }


    ///////////////////////////////////////////////////////////////////////////


//	 public Customer addCustomer(Customer cusLogin) {
//			String encodedPwd = pwdEncoder.encode(cusLogin.getPassword());// 加密
//			cusLogin.setPassword(encodedPwd);
//			return cuRepo.save(cusLogin);
//		}

    public boolean checkIfUsernameExist(String email) {
        Customer dbCustomer = cuRepo.findByEmail(email);

        if (dbCustomer != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsername(String customerName) {
        Customer ckname = cuRepo.findByCustomerName(customerName);

        if (ckname != null) {
            return true;
        } else {
            return false;
        }
    }


    public Customer checkLogin(String email, String inputPassword) {
        Customer dbCustomer = cuRepo.findByEmail(email);
        if (dbCustomer != null) {
            //比對加密過後
            if (pwdEncoder.matches(inputPassword, dbCustomer.getPassword())) {
                return dbCustomer;
            }
        }
        return null;
    }

    public boolean checkEmail(String email) {
        Customer dbCustomer = cuRepo.findByEmail(email);

        if (dbCustomer != null) {
            return true;
        } else {
            return false;
        }
    }

    public void insert(Customer cumer) {
        cuRepo.save(cumer);
    }




    public Customer getLoginDataByEmail(String email) {
        return cuRepo.findByEmail(email);

    }
    public Customer oauth2CheckLogin(String googleID) {//google登入
        return cuRepo.findByGoogleID(googleID);
    }

    //是否有googleID
    public boolean checkGoogleIdExistByEmail(String email) {
        Customer dbHotel = cuRepo.findByEmail(email);
        return dbHotel.getGoogleID() != null;
    }

    @Transactional //清除googleID
    public void clearGoogleID(String loginID) {//清除googleID
        cuRepo.clearGoogleID(loginID);
    }

    @Transactional //新增googleID
    public void bindGoogleID(String loginID, String newGoogleID) {
        cuRepo.bindGoogleID(loginID, newGoogleID);
    }

    @Transactional
    public Customer updateUserInfo(Integer loginID, Customer updatecustomer) {
        Optional<Customer> optional = cuRepo.findByLoginID(loginID);
        System.out.println("IN");
        if(optional.isPresent()) {
            System.out.println("INNNNNN");
            Customer cus = optional.get();
            cus.setCustomerName(updatecustomer.getCustomerName());
            cus.setSex(updatecustomer.getSex());
            cus.setBirthday(updatecustomer.getBirthday());
            cus.setPhone(updatecustomer.getPhone());
            cus.setHeadshot(updatecustomer.getHeadshot());
            cus.setCountry(updatecustomer.getCountry());
            cus.setCity(updatecustomer.getCity());
            cus.setRegion(updatecustomer.getRegion());
            cus.setStreet(updatecustomer.getStreet());
            cus.setPostalCode(updatecustomer.getPostalCode());
            System.out.println("OFFFFFFF");
            return cus;
        }else {
            System.out.println("Error");
            return null;
        }

    }
    
    public Boolean resetPwd(String email, String newPwd) {//更新密碼
        Customer dbCus = cuRepo.findByEmail(email);
        if (dbCus != null) {
            String encodedPwd = pwdEncoder.encode(newPwd);
            dbCus.setPassword(encodedPwd);
            cuRepo.save(dbCus);
            System.out.println("更新後密碼: " + dbCus.getPassword());
            return true;
        }
        return false;
    }

}
