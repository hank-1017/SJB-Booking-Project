package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.bean.Message;
import com.sjb.sjbProject.dto.MessageDto;
import com.sjb.sjbProject.service.HotelService;
import com.sjb.sjbProject.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true") // 跨域請求設定
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private HotelService hotelService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @GetMapping("/getSessionHotelID")
    public String getSessionHotelID(HttpSession httpSession) {
        Integer hotelID = (Integer) httpSession.getAttribute("hotelID");
        return hotelID.toString();
    }

    @GetMapping("/getSessionCustomerID")
    public String getSessionCustomerID(HttpSession httpSession) {
        Integer customerLoginID = (Integer) httpSession.getAttribute("customerLoginID");
        return customerLoginID.toString();
    }

    @PostMapping("saveMessage")
    public String saveMessage(@RequestParam("hotelID") String hotelID, @RequestParam("loginID") String loginID, @RequestParam("sender") String sender,@RequestParam("content") String content) {
        Message message = new Message();
        message.setContent(content);
        message.setLoginID(Integer.parseInt(loginID));
        message.setHotelID(Integer.parseInt(hotelID));
        message.setMessageTime(sdf.format(new Date()));
        message.setSender(sender);
        message.setStatus("未讀");
        messageService.addMessage(message);
        return "success";
    }

    @GetMapping("/getMessageListForCustomer")
    public List<MessageDto> getMessageListForCustomer(HttpSession httpSession) {
        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
        List<Integer> loginIds = messageService.getMessageListForCustomer(loginID);
        List<MessageDto> messageList = new ArrayList<>();

        for (Integer loginId : loginIds) {
            String hotelName = hotelService.findByHotelId(loginId).getHotelName();

            MessageDto message = new MessageDto();
            message.setHotelName(hotelName);
            message.setHotelID(loginId);

            messageList.add(message);
        }

        return messageList;
    }

    @GetMapping("/getMessageListForHotel")
    public List<Integer> getMessageListForHotel(HttpSession httpSession) {
        Integer hotelID = (Integer) httpSession.getAttribute("hotelID");
        return messageService.getMessageListForHotel(hotelID);
    }

    @GetMapping("/getByCustomer/{hotelID}")
    public List<Message> findAllMessageForCustomer(HttpSession httpSession, @PathVariable Integer hotelID) {
        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
        return messageService.findAllMessage(loginID,hotelID);
    }
    @GetMapping("/getByHotel/{hotelID}/{loginID}")
    public List<Message> getByHotel( @PathVariable Integer hotelID, @PathVariable Integer loginID) {
        return messageService.findAllMessage(loginID,hotelID);
    }


//
//
//    @PostMapping("/addFromCustomer")
//    public String addFromCustomer(@RequestParam("content") String content, HttpSession httpSession) {
//        Integer loginID = (Integer) httpSession.getAttribute("customerLoginID");
//        Integer msgHotel = (Integer) httpSession.getAttribute("MsgHotel");
//        Message message = new Message();
//        message.setContent(content);
//        message.setLoginID(loginID);
//        message.setHotelID(msgHotel);
//        message.setMessageTime(sdf.format(new Date()));
//        message.setStatus("customer");
//        messageService.addMessage(message);
//        return "success";
//    }
//
//    @PostMapping("/addFromHotel")
//    public String addFromHotel(@RequestParam("content") String content, HttpSession httpSession) {
//        Integer hotelID = (Integer) httpSession.getAttribute("hotelID");
//        Integer msgCustomer = (Integer) httpSession.getAttribute("MsgCustomer");
//        Message message = new Message();
//        message.setContent(content);
//        message.setHotelID(hotelID);
//        message.setLoginID(msgCustomer);
//        message.setMessageTime(sdf.format(new Date()));
//        message.setStatus("hotel");
//        messageService.addMessage(message);
//        return "success";
//    }
//
//
//
//    @GetMapping("/setMsgHotel/{hotelID}")
//    public String setMsgHotel(HttpSession httpSession, @PathVariable String hotelID) {
//        httpSession.setAttribute("MsgHotel", Integer.parseInt(hotelID));
//        return "success";
//    }
//
//    @GetMapping("/setMsgCustomer/{loginID}")
//    public String setMsgCustomer(HttpSession httpSession, @PathVariable String loginID) {
//        httpSession.setAttribute("MsgCustomer", Integer.parseInt(loginID));
//        return "success";
//    }
}
