package com.sjb.sjbProject.service;

import com.sjb.sjbProject.bean.HotelNotify;
import com.sjb.sjbProject.bean.Message;
import com.sjb.sjbProject.dto.MessageDto;
import com.sjb.sjbProject.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessage(Integer messageID) {
        messageRepository.deleteById(messageID);
    }

    @Transactional
    public void updateMessage(Message message) {
        messageRepository.save(message);
    }


    public List<Message> findAllMessageForHotel(Integer hotelID) {
        return messageRepository.findAllMessageForHotel(hotelID);
    }

    public List<Message> findAllMessageForCustomer(Integer loginID) {
        return messageRepository.findAllMessageForCustomer(loginID);
    }
    public List<Message> findAllMessage(Integer loginID,Integer hotelID) {
        return messageRepository.findAllMessage(hotelID,loginID);
    }

//    public List<Message> getMessageListForCustomer(Integer customerLoginID) {
//        return messageRepository.findLatestMessagesByCustomerID(customerLoginID);
//    }
    public List<Integer> getMessageListForHotel(Integer hotelID) {
        return messageRepository.findDistinctLoginIDsByHotelID(hotelID);
    }
    public List<Integer> getMessageListForCustomer(Integer customerLoginID) {
        return messageRepository.findDistinctLoginIDsBycustomerLoginID(customerLoginID);
    }



}
