package com.sjb.sjbProject.bean;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    //1104因綠界金流訂單編號限制20碼，且不允許-符號，所以改寫
    public String generateId() {
        UUID uuid =UUID.randomUUID();
        String id =uuid.toString().replaceAll("-", "");;
        return id.substring(0, 20);
    }
}
