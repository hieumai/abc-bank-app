package com.bank.abc.simdata.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    public static final String REDIS_KEY_PREFIX = "sms-verify-code--";

    @Autowired
    private RedissonClient redissonClient;

    public void sendVoucherCodeToPhoneNumber(String phoneNumber, String voucherCode) {
        // simulate sending voucher code to the user by SMS
        logger.info("SMS sent for voucher code " + voucherCode + " to " + phoneNumber);
    }

    private static String generateNewCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    public String generateSmsVerificationCode(String phoneNumber) {
        // Storing sms code to Redis with ttl for verification and automatically eviction
        RBucket<String> bucket = redissonClient.getBucket(REDIS_KEY_PREFIX + phoneNumber);
        if (bucket.isExists()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Phone number already has a verification code - please wait 60 seconds and try again.");
        }
        String smsCode = generateNewCode();
        bucket.set(smsCode, 60, TimeUnit.SECONDS);

        // simulate sending verification code to the user by SMS
        String smsContent = "Your login verification code is:" + smsCode + " - valid for 60 seconds";
        logger.info(smsContent);
        return smsContent;
    }

    public boolean verifySmsVerificationCode(String phoneNumber, String smsCode) {
        RBucket<String> bucket = redissonClient.getBucket(REDIS_KEY_PREFIX + phoneNumber);
        if (!bucket.isExists()) {
            return false;
        }
        String savedSmsCode = bucket.get();
        return savedSmsCode.equals(smsCode);
    }
}
