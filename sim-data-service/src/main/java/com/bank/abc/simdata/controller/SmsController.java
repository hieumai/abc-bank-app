package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @GetMapping("/verificationCode")
    public ResponseEntity<String> createSmsCode(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(smsService.generateSmsVerificationCode(phoneNumber));
    }
}
