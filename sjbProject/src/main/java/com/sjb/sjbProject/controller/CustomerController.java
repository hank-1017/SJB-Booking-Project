package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.Customer;
import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.HotelDetail;
import com.sjb.sjbProject.bean.HotelLogin;
import com.sjb.sjbProject.service.CustomerService;
import com.sjb.sjbProject.service.MailService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true")
public class CustomerController {

    @Autowired
    private CustomerService cuService;
    @Autowired
    private MailService mailService;


    // 取得大頭照
    @GetMapping("/user/headshot")
    public ResponseEntity<byte[]> getUserHeadShot(HttpSession httpsession) {
        Object loginIDObj = httpsession.getAttribute("customerLoginID");
        if (loginIDObj != null) {
            Integer loginID = Integer.valueOf(loginIDObj.toString());
            Customer result = cuService.findById(loginID);
            System.out.println("UserID: " + loginID + " getUserHeadShot");
            return ResponseEntity.ok(result.getHeadshot());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // 取得大頭照2
    @GetMapping("/getheadshot/{loginID}")
    public ResponseEntity<byte[]> getheadshot(@PathVariable Integer loginID) {
        Customer result = cuService.findById(loginID);
        System.out.println("UserID: " + loginID + " getUserHeadShot");
        return ResponseEntity.ok(result.getHeadshot());

    }


    //會員註冊
    @PostMapping("/user/register")
    public Customer userRegister(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("customerName") String customerName,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("country") String country,
                                 @RequestParam("city") String city,
                                 @RequestParam("street") String street,
                                 @RequestParam("region") String region,
                                 @RequestParam("postalCode") String postalCode,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") // 日期格式化
                                 @RequestParam("birthday") Date birthday,
                                 @RequestParam("headshot") MultipartFile headshotFile) throws IOException {
        Customer cus = new Customer(email, password, customerName, sex, birthday, phone, headshotFile.getBytes(), country, city, region, street, postalCode);
        boolean isExist = cuService.checkIfUsernameExist(cus.getEmail());
        if (isExist) {
            return new Customer();
        }
        System.out.println("###############################");
        System.out.println(cus.getPassword());
        System.out.println("###############################");
        Customer addCustomer = cuService.AddCustomer(cus);
        System.out.println("註冊成功");
        return addCustomer;
    }


    @PostMapping("/checkEmail")
    public String checkEmail(@RequestParam String email) {
        System.out.println("checkEmail: " + email);
        if (cuService.checkEmail(email)) {
            return "duplicate";
        }
        return "ok";
    }


    //登入判斷
    @PostMapping("/user/login")
    public String checkUserLogin(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 HttpSession httpsession) {
        Customer result = cuService.checkLogin(email, password);
        if (result != null) {
            System.out.println("登入成功");
            httpsession.setAttribute("customerLoginID", result.getLoginID());
            return "Y";//
        } else {
            System.out.println("登入fail");
            return "N";
        }
    }

    @GetMapping("/user/getLoginInfo")
    public ResponseEntity<Customer> getUserLoginData(HttpSession httpsession) {
        Object loginIDObj = httpsession.getAttribute("customerLoginID");
        if (loginIDObj != null) {
            int loginID = Integer.parseInt(loginIDObj.toString());
            Customer result = cuService.findById(loginID);
            System.out.println("UserID: " + result.getLoginID() + " getUserLoginData");
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    // 檢查用戶是否已登入
    @GetMapping("/checkLoginStatus")
    public String checkLoginStatus(HttpSession httpsession) {
        Object loginIDObj = httpsession.getAttribute("customerLoginID");
        if (loginIDObj != null) {
            return "loggedIn"; // 用户已登录
        }
        return "notLoggedIn"; // 用户未登录
    }

    // 登出
    @GetMapping("/user/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession != null) {
            System.out.println(httpSession.getAttribute("customerLoginID") + " Logout");
            httpSession.invalidate();
            return "Y";
        }
        return "N";
    }

    @GetMapping("/customer/name")
    public String findCustomerName(@RequestParam String name) {
        Customer cname = cuService.findCustomerByCustomerName(name);
        if (cname != null) {
            return cname.getCity();
        }
        return null;
    }


    @GetMapping("/customer/id")
    public String getCustomerProfile(Model model, HttpSession session) {
        // 檢查用戶是否已登入
        Customer result = (Customer) session.getAttribute("loginCustomer");
        if (result != null) {
            int id = result.getLoginID();
            Customer customer = cuService.findById(id);
            if (customer != null) {
                // 傳遞用戶個人資料到 Thymeleaf 模板
                model.addAttribute("customer", customer);
            }
        }
        // 返回 Thymeleaf 模板的名稱
        return "users/userDataPage";
    }

    @PutMapping("customer/update")
    public String updateUserById(
            @RequestParam("customerName") String customerName,
            @RequestParam("sex") String sex,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam("birthday") Date birthday,
            @RequestParam("phone") String phone,
            @RequestParam(name = "headshot", required = false) MultipartFile headshotFile,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("region") String region,
            @RequestParam("street") String street,
            @RequestParam("postalCode") String postalCode, HttpSession httpSession) throws IOException {
        Object loginIDObj = httpSession.getAttribute("customerLoginID");
        System.out.println("customerLoginIDObj =" + loginIDObj);
        if (loginIDObj != null) {
            Integer loginID = Integer.valueOf(loginIDObj.toString());
            byte[] headshotBytes = null;

            if (headshotFile != null && !headshotFile.isEmpty()) {
                // 如果 headshotFile 不為 null，則讀取文件內容
                headshotBytes = headshotFile.getBytes();
            } else {
                // 如果 headshotFile 為 null，則從資料庫中抓取原本的大頭照
                Customer originalCustomer = cuService.findById(loginID);
                if (originalCustomer != null) {
                    headshotBytes = originalCustomer.getHeadshot();
                }
            }


            Customer updatecustomer = new Customer(customerName, sex, birthday, phone, headshotBytes, country, city, region, street, postalCode);
            cuService.updateUserInfo(loginID, updatecustomer);
            System.out.println("更新成功");
            return "Y";
        }
        System.out.println("更新失敗");
        return "N";
    }


    @GetMapping("/customer/edit")
    public String editPage(@RequestParam("loginID") Integer loginID, Model model) {
        System.out.println("AAAA");
        Customer customer = cuService.findById(loginID);
        model.addAttribute("Customer", customer);

        return "users/editPage";
    }

    @PostMapping("/customer/edit")
    public String submitUpdate(@ModelAttribute("Customer") Customer cumer) {
        System.out.println(cumer.getBirthday());
        cuService.insert(cumer);

        return "redirect:/users/userDataPage";
    }


    //Google 登入
    @PostMapping("/googleLogin")
    public String userGoogleLogin(@RequestBody Map<String, String> googleData, HttpSession httpSession) {

        if (googleData != null) {
            String googleID = googleData.get("sub");
            String googleEmail = googleData.get("email");
            Customer result = cuService.oauth2CheckLogin(googleID);

            if (result != null) {
                httpSession.setAttribute("customerLoginID", result.getLoginID());
                System.out.println("GoogleLogin: " + googleEmail + " GoogleID: " + googleID);
                System.out.println(result.getLoginID() + "-" + result.getCustomerName() + " Login Success !!");
                return "Y";
            } else {
                System.out.println("GoogleLogin Fail!!" + " GoogleID: " + googleID);
                boolean checkEmail = cuService.checkEmail(googleEmail);
                if (checkEmail) {
                    return "E";
                }
            }
        }
        return "N";
    }

    //清除googleID
    @GetMapping("/clearGoogleID")
    public String userClearGoogleID(HttpSession httpSession) {

        if (httpSession.getAttribute("customerLoginID").toString() != null) {
            String loginID = httpSession.getAttribute("loginId").toString();
            cuService.clearGoogleID(loginID);
            System.out.println(loginID + " 已清除googleID");
            return "Y";
        }
        return "N";
    }

    //綁定googleID
    @PostMapping("/bindGoogleID")
    public String userBindGoogleID(@RequestBody Map<String, String> googleData, HttpSession httpSession) {

        String googleID = googleData.get("sub");
        Object loginIdObj = httpSession.getAttribute("customerLoginID");
        if (loginIdObj != null) { //登入後綁定googleID
//            System.out.println(googleData);
            Customer result = cuService.oauth2CheckLogin(googleID);
            if (result == null) {
                String loginID = httpSession.getAttribute("loginId").toString();
                cuService.bindGoogleID(loginID, googleID);
                System.out.println(loginID + " 已寫入googleID: " + googleID);
                return "Y";
            }
        } else {//在登入前綁定googleID
            String googleEmail = googleData.get("email");
            Customer result = cuService.getLoginDataByEmail(googleEmail);
            if (result != null) {
                String loginID = String.valueOf(result.getLoginID());
                cuService.bindGoogleID(loginID, googleID);
                System.out.println(loginID + " 已寫入googleID: " + googleID);
                httpSession.setAttribute("customerLoginID", result.getLoginID());
                System.out.println("GoogleLogin: " + googleEmail + " GoogleID: " + googleID);
                System.out.println(result.getLoginID() + "-" + result.getCustomerName() + " Login Success !!");
                return "E";
            }
        }
        System.out.println(" 寫入googleID失敗: " + googleID);
        return "N";
    }

    //以googleID進行註冊
    @PostMapping("/registerByGoogle")
    public String userRegisterByGoogle(@RequestBody Map<String, String> googleData, HttpSession httpSession) {

        String googleID = googleData.get("sub");
        String googleEmail = googleData.get("email");
        Customer checkID = cuService.oauth2CheckLogin(googleID);
        boolean checkEmail = cuService.checkEmail(googleEmail);
        System.out.println("checkIfEmailExist: " + checkEmail);
        if (checkID == null) {
            if (checkEmail) {
                System.out.println("googleID: " + googleID + "未使用 但email已被註冊");
                return "E";
            }
            httpSession.setAttribute("UserRegisterGoogleID", googleID);
            httpSession.setAttribute("registerMail", googleEmail);
            System.out.println("GoogleID: " + googleID + "可註冊");
            return "Y";
        }
        System.out.println("googleID: " + googleID + "已綁定帳號 無法註冊");
        return "N";
    }

    //檢查是否綁定google
    @GetMapping("/checkGoogleID")
    public String userCheckGoogleID(HttpSession httpSession) {
        String loginEmail = httpSession.getAttribute("loginEmail").toString();
        if (cuService.checkGoogleIdExistByEmail(loginEmail)) {
            System.out.println(loginEmail + " 已綁定");
            return "Y";
        }
        System.out.println(loginEmail + " 未綁定");
        return "N";
    }

    //訂閱email
    @PostMapping("/subscribe")
    public String subscribe(@RequestParam("email") String email) {
        System.out.println("訂閱email: " + email);
        mailService.sendMail(email, "訂閱成功", "您已成功訂閱SJB.com的電子報，謝謝您的訂閱");
        return "Y";
    }

    @GetMapping("/checkUserExist/{loginID}")
    public Boolean checkHotelExist(@PathVariable Integer loginID) {
        Customer customer = cuService.findById(loginID);
        return customer != null;
    }
    
  //重設密碼-開始
    //寄送驗證碼
    @PostMapping("/customer/forget_password")
    public String getMailCodeByCustomer(@RequestParam("email") String email, HttpSession httpSession) {
//        System.out.println("忘記密碼輸入email: "+email);
        if (cuService.checkEmail(email)) {//判斷信箱是否已註冊
            //寄送驗證碼
            mailService.sendResetPwdCodeMail(email, httpSession);
            httpSession.setAttribute("resetMail", email);

            return "Y";
        } else {
            return "N";
        }
    }


    @GetMapping("/customer/getResetMail")
    public String getResetMail(HttpSession httpSession) {
        System.out.println("getResetMail: " + httpSession.getAttribute("resetMail"));
        return (String) httpSession.getAttribute("resetMail");
    }



    //重設密碼-結束
    //密碼重設
    @PostMapping("/customer/reset_password")
    public String postResetPassword(@RequestParam("newPwd") String newPwd,
                                    @RequestParam("verificationCode") String verificationCode,
                                    HttpSession httpSession) {

        String resetMail = httpSession.getAttribute("resetMail").toString();
        String sessionCode = httpSession.getAttribute("verificationCode").toString();


        System.out.println("更新Pwd Email: " + resetMail);
        System.out.println("Session驗證碼: " + sessionCode);
        System.out.println("輸入驗證碼: " + verificationCode);

        if (Objects.equals(verificationCode, sessionCode)) {//比對驗證碼

            Boolean pwdResult = cuService.resetPwd(resetMail, newPwd);//更新密碼

            if (pwdResult) {
                httpSession.removeAttribute("resetMail");
                httpSession.removeAttribute("verificationCode");
                System.out.println(resetMail + "驗證碼已刪除");
                mailService.sendMail(resetMail, "密碼變更成功!!!", "您的帳號: " + resetMail + "\n密碼已變更" + "\n變更時間: " + new Date());//寄送密碼變更通知
                return "Y";
            } else {
                return "N";
            }
        } else {
            return "N";
        }
    }



}

