package com.bank.abc.simdata.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    public void sendVoucherCodeToPhoneNumber(String phoneNumber, String voucherCode) {
        // simulate sending voucher code to the user by SMS
        logger.info("SMS sent for voucher code " + voucherCode + " to phoneNumber");
    }
}
