package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.Hotel;
import com.sjb.sjbProject.bean.HotelDetail;
import com.sjb.sjbProject.bean.HotelLogin;
import com.sjb.sjbProject.service.HotelLoginService;
import com.sjb.sjbProject.service.HotelService;
import com.sjb.sjbProject.service.MailService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true") // 跨域請求設定
public class HotelLoginController {
    @Autowired
    private HotelLoginService hotelloginService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private MailService mailService;


    //檢查token是否有效
    @PostMapping("/checkToken")
    public String checkToken(@RequestParam("token") String token) {
        String loginID = hotelloginService.checkToken(token);
        if (loginID != null) {
            // 驗證成功
            return "Y";
        }
        return "N";
    }


    //登入判斷
    @PostMapping("/login")
    public String checkHotelLogin(@RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  HttpSession httpSession) {

        HotelLogin result = hotelloginService.checkLogin(email, password);

        if (result != null) {
            httpSession.setAttribute("loginName", result.getLoginName());
            httpSession.setAttribute("loginEmail", result.getEmail());
            httpSession.setAttribute("loginId", result.getLoginID());
            System.out.println(result.getLoginID() + "-" + result.getLoginName() + " Login Success !!");

            return "Y";
        } else {
            System.out.println(email + " 登入失敗");
        }
        return "N";
    }

    //登出(刪除全部Session)
    @GetMapping("/logout")
    public String hotelLogout(HttpSession httpSession) {
        if (httpSession != null) {
            System.out.println(httpSession.getAttribute("loginId") + " Logout");
            httpSession.invalidate();
            return "Y";
        }
        return "N";
    }

    //取得登入資訊
    @PostMapping("/getLoginInfo")
    public ResponseEntity<Map<String, Object>> getLoginInfo(HttpSession httpSession) {
        Object loginIdObj = httpSession.getAttribute("loginId");
        Object loginNameObj = httpSession.getAttribute("loginName");
        Object loginEmailObj = httpSession.getAttribute("loginEmail");
        Object hotelIDObj = httpSession.getAttribute("hotelID");
        if (loginIdObj != null) {
            String hotelName = hotelService.findByHotelId(Integer.parseInt(hotelIDObj.toString())).getHotelName();
            Map<String, Object> loginInfo = new HashMap<>();
            loginInfo.put("loginId", loginIdObj.toString());
            loginInfo.put("loginName", loginNameObj.toString());
            loginInfo.put("loginEmail", loginEmailObj.toString());
            loginInfo.put("hotelID", hotelIDObj.toString());
            loginInfo.put("hotelName", hotelName);
            System.out.println("loginId: " + loginIdObj.toString() + " getLoginInfo");
            System.out.println("hotelName: " + hotelName);
            return ResponseEntity.ok(loginInfo);
        }
        // 驗證失敗
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    //登入狀態
    @GetMapping("/checkLoginStatus")
    public String checkLoginStatus(HttpSession httpSession) {
        Object loginIdObj = httpSession.getAttribute("loginId");
        if (loginIdObj != null) {
            return "Y";
        }
        return "N";
    }

    //取得頭像1
    @GetMapping("/getHeadshot")
    public ResponseEntity<byte[]> getHeadshot1(HttpSession httpSession) {
        Object loginIdObj = httpSession.getAttribute("loginId");
        if (loginIdObj != null) {
            HotelLogin result = hotelloginService.getLoginData(loginIdObj.toString());
            return ResponseEntity.ok(result.getHeadshot());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    //取得頭像2
    @GetMapping("/getHeadshot/{hotelID}")
    public ResponseEntity<byte[]> getHeadshot2(@PathVariable Integer hotelID) {
        Hotel res = hotelService.findByHotelId(hotelID);
        HotelLogin result = hotelloginService.getLoginData(res.getLoginid().toString());
        return ResponseEntity.ok(result.getHeadshot());
    }


    //更新頭像
    @PostMapping("/updateHeadshot")
    @ResponseBody
    public String updateHeadshot(HttpSession httpSession, @RequestParam("headshot") MultipartFile headshot) {
        String loginID = httpSession.getAttribute("loginId").toString();
        if (headshot.isEmpty() || loginID.isEmpty()) {
            System.out.println("headshot is empty");
            return "N";
        }
        try {
            hotelloginService.updateHeadshot(loginID, headshot.getBytes());
            System.out.println("headshot update success");
            return "Y";
        } catch (Exception e) {

            System.out.println("headshot update fail");
            return "N";
        }
    }


    //Google 登入
    @PostMapping("/dashboard")
    public String hotelDashboardPage(@RequestBody Map<String, String> googleData, HttpSession httpSession) {

        if (googleData != null) {
            String googleID = googleData.get("sub");
            String googleEmail = googleData.get("email");
            HotelLogin result = hotelloginService.oauth2CheckLogin(googleID);

            if (result != null) {
                httpSession.setAttribute("loginId", result.getLoginID());
                httpSession.setAttribute("loginEmail", result.getEmail());
                httpSession.setAttribute("loginName", result.getLoginName());
                System.out.println("GoogleLogin: " + googleEmail + " GoogleID: " + googleID);
                System.out.println(result.getLoginID() + "-" + result.getLoginName() + " Login Success !!");
                return "Y";
            } else {
                System.out.println("GoogleLogin Fail!!" + " GoogleID: " + googleID);
                boolean checkEmail = hotelloginService.checkIfEmailExist(googleEmail);
                if (checkEmail) {
                    return "E";
                }
            }
        }
        return "N";
    }

    //清除googleID
    @GetMapping("/clearGoogleID")
    public String clearGoogleID(HttpSession httpSession) {

        if (httpSession.getAttribute("loginId").toString() != null) {
            String loginID = httpSession.getAttribute("loginId").toString();
            hotelloginService.clearGoogleID(loginID);
            System.out.println(loginID + " 已清除googleID");
            return "Y";
        }
        return "N";
    }

    //綁定googleID
    @PostMapping("/bindGoogleID")
    public String bindGoogleID(@RequestBody Map<String, String> googleData, HttpSession httpSession) {

        String googleID = googleData.get("sub");
        Object loginIdObj = httpSession.getAttribute("loginId");
        if (loginIdObj != null) { //登入後綁定googleID
//            System.out.println(googleData);
            HotelLogin result = hotelloginService.oauth2CheckLogin(googleID);
            if (result == null) {
                String loginID = httpSession.getAttribute("loginId").toString();
                hotelloginService.bindGoogleID(loginID, googleID);
                System.out.println(loginID + " 已寫入googleID: " + googleID);
                return "Y";
            }
        } else {//在登入前綁定googleID
            String googleEmail = googleData.get("email");
            HotelLogin result = hotelloginService.getLoginDataByEmail(googleEmail);
            if (result != null) {
                String loginID = String.valueOf(result.getLoginID());
                hotelloginService.bindGoogleID(loginID, googleID);
                System.out.println(loginID + " 已寫入googleID: " + googleID);
                httpSession.setAttribute("loginEmail", result.getEmail());
                httpSession.setAttribute("loginId", result.getLoginID());
                httpSession.setAttribute("loginName", result.getLoginName());
                System.out.println("GoogleLogin: " + googleEmail + " GoogleID: " + googleID);
                System.out.println(result.getLoginID() + "-" + result.getLoginName() + " Login Success !!");
                return "E";
            }
        }
        System.out.println(" 寫入googleID失敗: " + googleID);
        return "N";
    }

    //以googleID進行註冊
    @PostMapping("/RegisterByGoogle")
    public String registerByGoogle(@RequestBody Map<String, String> googleData, HttpSession httpSession) {

        String googleID = googleData.get("sub");
        String googleEmail = googleData.get("email");
        HotelLogin checkID = hotelloginService.oauth2CheckLogin(googleID);
        boolean checkEmail = hotelloginService.checkIfEmailExist(googleEmail);
        System.out.println("checkIfEmailExist: " + checkEmail);
        if (checkID == null) {
            if (checkEmail) {
                System.out.println("googleID: " + googleID + "未使用 但email已被註冊");
                return "E";
            }
            httpSession.setAttribute("RegisterGoogleID", googleID);
            httpSession.setAttribute("registerMail", googleEmail);
            System.out.println("GoogleID: " + googleID + "可註冊");
            return "Y";
        }
        System.out.println("googleID: " + googleID + "已綁定帳號 無法註冊");
        return "N";
    }

    //檢查是否綁定google
    @GetMapping("/checkGoogleID")
    public String checkGoogleID(HttpSession httpSession) {
        String loginEmail = httpSession.getAttribute("loginEmail").toString();
        if (hotelloginService.checkGoogleIdExistByEmail(loginEmail)) {
            System.out.println(loginEmail + " 已綁定");
            return "Y";
        }
        System.out.println(loginEmail + " 未綁定");
        return "N";
    }


    //註冊開始
    //轉註冊mail驗證頁
    @GetMapping("/startRegister") //如何讓此頁跳出alert:
    public String registerCheckEmail(HttpSession httpSession) {
        httpSession.removeAttribute("verificationCode");
        httpSession.removeAttribute("registerChkMail");
        return "hotel/registerCheckEmail";
    }

    //註冊前寄送驗證碼
    @PostMapping("/startRegister")
    public String getMailCodeForRegister(@RequestParam("email") String email, HttpSession httpSession) {
        if (!hotelloginService.checkIfEmailExist(email)) {//判斷信箱是否已註冊
            //寄送驗證碼
            mailService.sendRegisterPwdCodeMail(email, httpSession);
            httpSession.setAttribute("registerChkMail", email);//未驗證信箱存入session
            return "Y";
        } else {
            return "N";
        }
    }

    //註冊前比對Email驗證碼
    @PostMapping("/registerCode")
    public String checkRegisterCode(@RequestParam("verificationCode") String verificationCode,
                                    HttpSession httpSession) {

        if (httpSession.getAttribute("verificationCode") != null) {
            String registerChkMail = httpSession.getAttribute("registerChkMail").toString();//取得未驗證信箱
            String sessionCode = httpSession.getAttribute("verificationCode").toString();

            System.out.println("Session註冊驗證碼: " + sessionCode);
            System.out.println("輸入註冊驗證碼: " + verificationCode);

            if (Objects.equals(verificationCode, sessionCode)) {//比對驗證碼
                httpSession.removeAttribute("verificationCode");
                httpSession.setAttribute("registerMail", registerChkMail);//已驗證信箱存入session
                httpSession.removeAttribute("registerChkMail");
                System.out.println(registerChkMail + "註冊驗證碼已刪除");
                return "Y";
            }
        }
        return "N";
    }

    //註冊結束
    //註冊寫入
    @PostMapping("/register")
    public String postHotellogin(@RequestParam("email") String email,
                                 @RequestParam("pass") String pass,
                                 @RequestParam("loginName") String loginName,
                                 @RequestParam("hotelName") String hotelName,
                                 @RequestParam("hotelType") String hotelType,
                                 @RequestParam("hotelStar") String hotelStar,
                                 @RequestParam("tel") String tel,
                                 @RequestParam("country") String country,
                                 @RequestParam("city") String city,
                                 @RequestParam("region") String region,
                                 @RequestParam("street") String street,
                                 @RequestParam("postalCode") String postalCode,
                                 @RequestParam("GUInumber") String GUInumber,
                                 @RequestParam("businessName") String businessName,
                                 @RequestParam("openYear") String openYear,
                                 @RequestParam(name = "headshot", required = false) MultipartFile headshot,
                                 HttpSession httpSession) throws IOException {
        String mapURL = "0";
        String state = "false"; //預設為停業
        String googleID = null;
        String lineID = null;
        byte[] headshotBytes = null;
        Object registerGoogleID = httpSession.getAttribute("RegisterGoogleID");
        if (registerGoogleID != null) {//判斷是否以googleID註冊
            googleID = registerGoogleID.toString();
        }
        if (headshot == null) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/img/headshot.png");
            assert inputStream != null;
            headshotBytes = IOUtils.toByteArray(inputStream);
        } else {
            headshotBytes = headshot.getBytes();
        }
        HotelLogin hotelLogin = new HotelLogin(email, pass, loginName, headshotBytes, googleID, lineID);

        Hotel hotel = new Hotel(hotelName, hotelType, hotelStar, tel, country, city, region, street, postalCode, Boolean.parseBoolean(state));
        HotelDetail hotelDetail = new HotelDetail(mapURL, GUInumber, businessName, Integer.parseInt(openYear));

        if (Objects.equals(httpSession.getAttribute("registerMail").toString(), email)) {//比對session中已驗證信箱與輸入之信箱
            System.out.println("進入");
            hotelloginService.insert(hotelLogin, hotel, hotelDetail);
            httpSession.setAttribute("loginName", hotelLogin.getLoginName());
            httpSession.setAttribute("loginId", hotelLogin.getLoginID());
            httpSession.setAttribute("loginEmail", hotelLogin.getEmail());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            mailService.sendMail(email, "已完成住宿帳號註冊",
                    "您的帳號: " + email +
                            "\n帳號姓名" + loginName +
                            "\n住宿名稱: " + hotelName +
                            "\n註冊時間: " + sdf.format(new Date()));

            return "Y";
        } else {
            System.out.println("註冊失敗，信箱未經過驗證");
            return "N";
        }
    }

    @GetMapping("/getRegisterMail")
    public String getRegisterMail(HttpSession httpSession) {
        Object registerMailObj = httpSession.getAttribute("registerMail");
        if (registerMailObj != null) {
            return registerMailObj.toString();
        }
        return null;
    }


    //重設密碼-開始
    //寄送驗證碼
    @PostMapping("/forget_password")
    public String getMailCodeByHotel(@RequestParam("email") String email, HttpSession httpSession) {
//        System.out.println("忘記密碼輸入email: "+email);
        if (hotelloginService.checkIfEmailExist(email)) {//判斷信箱是否已註冊
            //寄送驗證碼
            mailService.sendResetPwdCodeMail(email, httpSession);
            httpSession.setAttribute("resetMail", email);

            return "Y";
        } else {
            return "N";
        }
    }


    @GetMapping("/getResetMail")
    public String getResetMail(HttpSession httpSession) {
        System.out.println("getResetMail: " + httpSession.getAttribute("resetMail"));
        return (String) httpSession.getAttribute("resetMail");
    }

    @PutMapping("/updateLoginName")
    public String updateLoginName(HttpSession httpSession, @RequestParam String loginName) {
        Object loginIdObj = httpSession.getAttribute("loginId");
        if (loginIdObj != null) {
            String loginID = loginIdObj.toString();
            hotelloginService.updateLoginName(loginID, loginName);
            httpSession.setAttribute("loginName", loginName);
            return "Y";
        }
        return "N";
    }


    //重設密碼-結束
    //密碼重設
    @PostMapping("/reset_password")
    public String postResetPassword(@RequestParam("newPwd") String newPwd,
                                    @RequestParam("verificationCode") String verificationCode,
                                    HttpSession httpSession) {

        String resetMail = httpSession.getAttribute("resetMail").toString();
        String sessionCode = httpSession.getAttribute("verificationCode").toString();


        System.out.println("更新Pwd Email: " + resetMail);
        System.out.println("Session驗證碼: " + sessionCode);
        System.out.println("輸入驗證碼: " + verificationCode);

        if (Objects.equals(verificationCode, sessionCode)) {//比對驗證碼

            Boolean pwdResult = hotelloginService.resetPwd(resetMail, newPwd);//更新密碼

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

    //測試line登入用
    @GetMapping("/testline")
    public String testline() {
        return "hotel/test1";
    }

    @PostMapping("/createline")
    public String createline(@RequestParam("aud") String LineID) {
        System.out.println(LineID);
        return null;
    }


}


