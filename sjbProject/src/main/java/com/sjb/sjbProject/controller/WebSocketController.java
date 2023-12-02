package com.sjb.sjbProject.controller;

import com.sjb.sjbProject.service.CustomerService;
import com.sjb.sjbProject.service.HotelService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/WebSocketServer/{userType}/{userId}/{hotelId}")
public class WebSocketController {

    private static final Map<String, Session> userSessions = new ConcurrentHashMap<>();
    private static final Map<String, Session> hotelSessions = new ConcurrentHashMap<>();

    @OnOpen  //userType: 0=hotel, 1=user
    public void onOpen(Session session, @PathParam("userId") String userId,
                       @PathParam("hotelId") String hotelId, @PathParam("userType") String userType) {

        if (userType.equals("1")) {
            userSessions.put(userId, session);
            sendMessageToHotel(hotelId, "U"+userId +": "+"(用戶)已上線");
            sendMessageToUser(userId, "U"+userId+": "+"您已連線至伺服器");
        } else if (userType.equals("0")) {
            hotelSessions.put(hotelId, session);
            sendMessageToUser(userId, "H"+hotelId+": "+"(商家)已上線");
            sendMessageToHotel(hotelId, "H"+hotelId +": "+"您已連線至伺服器");
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId,
                          @PathParam("hotelId") String hotelId, @PathParam("userType") String userType) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sdf.format(new java.util.Date());

        if (userType.equals("1")) {
            sendMessageToHotel(hotelId, "U"+userId+": "+message+"#"+time);
            System.out.println("用戶 "+userId+": "+message);
        } else if (userType.equals("0")) {
            sendMessageToUser(userId, "H"+hotelId+": "+message+"#"+time);
            System.out.println("商家 "+hotelId+": "+message);
        }
    }


    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId,
                        @PathParam("hotelId") String hotelId, @PathParam("userType") String userType) {

        if (userType.equals("1")) {
            sendMessageToHotel(hotelId, "U"+userId+": "+"(用戶)已離線");
            userSessions.remove(userId);
//            System.out.println("用戶 " + "U"+userId + " 已離線.");
        } else if (userType.equals("0")) {
            sendMessageToUser(userId, "H"+hotelId+": "+"(商家)已離線");
            hotelSessions.remove(hotelId);
//            System.out.println("商家 " + "H"+hotelId + " 已離線.");
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("錯誤:" + throwable);
        try {
            session.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }


    private void sendMessageToUser(String userId, String message) {

        Session userSession = userSessions.get(userId);

        if (userSession != null && userSession.isOpen()) {

            userSession.getAsyncRemote().sendText(message);

        } else {
            System.out.println("User session " + userId + " is null or closed!");
        }

    }

    private void sendMessageToHotel(String hotelId, String message) {
        Session hotelSession = hotelSessions.get(hotelId);

        if (hotelSession != null && hotelSession.isOpen()) {

            hotelSession.getAsyncRemote().sendText(message);

        } else {
            System.out.println("Hotel session " + hotelId + " is null or closed!");
        }

    }

//    private void sendMessage(Session session, String message) {
//        if (session != null) {
//            try {
//                session.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                // Handle send error
//            }
//        }
//    }
}