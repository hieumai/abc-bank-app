package com.bank.abc.simdata.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SmsServiceTest {
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String VOUCHER_CODE = "voucherCode";
    public static final String SMS_CODE = "smsCode";
    @Mock
    private Appender<ILoggingEvent> mockedAppender;

    @Captor
    private ArgumentCaptor<ILoggingEvent> loggingEventCaptor;

    @Mock
    private RedissonClient mockRedissonClient;

    @Mock
    private RBucket<String> mockRBucket;

    @Captor
    private ArgumentCaptor<String> codeCapture;

    @InjectMocks
    private SmsService smsService;

    @BeforeEach
    public void setup() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockedAppender);
        root.setLevel(Level.INFO);
    }

    @Test
    void sendVoucherCodeToPhoneNumberShouldLogInfoForSimulating() {
        smsService.sendVoucherCodeToPhoneNumber(PHONE_NUMBER, VOUCHER_CODE);
        verifyLog("SMS sent for voucher code " + VOUCHER_CODE + " to " + PHONE_NUMBER);
    }

    private void verifyLog(String logMessage) {
        verify(mockedAppender, times(1)).doAppend(loggingEventCaptor.capture());
        ILoggingEvent loggingEvent = loggingEventCaptor.getAllValues().get(0);
        assertThat(loggingEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(loggingEvent.getMessage()).isEqualTo(logMessage);
    }

    @Test
    void generateSmsVerificationCodeShouldGenerateNewCodeAndSetToRedisWhileSendSMSToTheUser() {
        when(mockRedissonClient.getBucket(SmsService.REDIS_KEY_PREFIX + PHONE_NUMBER)).thenReturn((RBucket) mockRBucket);
        when(mockRBucket.isExists()).thenReturn(false);

        String smsContent = smsService.generateSmsVerificationCode(PHONE_NUMBER);
        verify(mockRBucket).set(codeCapture.capture(), eq(60L), eq(TimeUnit.SECONDS));
        String resultSms = codeCapture.getValue();
        String expectedSmsContent = "Your login verification code is:" + resultSms + " - valid for 60 seconds";
        verifyLog(expectedSmsContent);
        assertThat(smsContent).isEqualTo(expectedSmsContent);
    }

    @Test
    void generateSmsVerificationCodeShouldThrowResponseStatusExceptionWhenCodeAlreadyExistsForAPhoneNumber() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            when(mockRedissonClient.getBucket(SmsService.REDIS_KEY_PREFIX + PHONE_NUMBER)).thenReturn((RBucket) mockRBucket);
            when(mockRBucket.isExists()).thenReturn(true);
            smsService.generateSmsVerificationCode(PHONE_NUMBER);
        });

        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("Phone number already has a verification code - please wait 60 seconds and try again.");
    }

    @Test
    void verifySmsVerificationCodeShouldReturnTrueIfSmsCodeExistsForThePhoneNumberOnRedis() {
        when(mockRedissonClient.getBucket(SmsService.REDIS_KEY_PREFIX + PHONE_NUMBER)).thenReturn((RBucket) mockRBucket);
        when(mockRBucket.isExists()).thenReturn(true);
        when(mockRBucket.get()).thenReturn(SMS_CODE);
        assertThat(smsService.verifySmsVerificationCode(PHONE_NUMBER, SMS_CODE)).isTrue();
    }

    @Test
    void verifySmsVerificationCodeShouldReturnFalseIfSmsCodeNotExistsForThePhoneNumberOnRedis() {
        when(mockRedissonClient.getBucket(SmsService.REDIS_KEY_PREFIX + PHONE_NUMBER)).thenReturn((RBucket) mockRBucket);
        when(mockRBucket.isExists()).thenReturn(false);
        assertThat(smsService.verifySmsVerificationCode(PHONE_NUMBER, SMS_CODE)).isFalse();
    }

    @Test
    void verifySmsVerificationCodeShouldReturnFalseIfSmsCodeExistsButNotMatchedForThePhoneNumberOnRedis() {
        when(mockRedissonClient.getBucket(SmsService.REDIS_KEY_PREFIX + PHONE_NUMBER)).thenReturn((RBucket) mockRBucket);
        when(mockRBucket.isExists()).thenReturn(true);
        when(mockRBucket.get()).thenReturn(SMS_CODE);
        assertThat(smsService.verifySmsVerificationCode(PHONE_NUMBER, "wrongCode")).isFalse();
    }
}