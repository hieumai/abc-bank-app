package com.voucher.provider.code.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
public class VoucherCodeController {
    @GetMapping("/code")
    public String getCode() throws InterruptedException {
        // using Thread.sleep to simulate time the real 3rd party service will have to wait
        Thread.sleep(new Random().nextInt(12000));
        return UUID.randomUUID().toString();
    }
}
