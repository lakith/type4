package com.spring.starter.controller;

import com.spring.starter.service.impl.SMSSErvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class SmsController {

    @Autowired
    SMSSErvice smssErvice;

    @GetMapping("send")
    public ResponseEntity<?> sendSms(){
        smssErvice.sendSMS();
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
