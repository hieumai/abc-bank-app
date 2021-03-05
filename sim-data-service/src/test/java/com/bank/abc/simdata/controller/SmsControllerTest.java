package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.services.SmsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsControllerTest {
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String CODE = "code";

    @Mock
    private SmsService mockSmsService;

    @InjectMocks
    private SmsController smsController;

    @Test
    void createSmsCodeShouldReturnResultFromSmsService() {
        when(mockSmsService.generateSmsVerificationCode(PHONE_NUMBER)).thenReturn(CODE);
        ResponseEntity<String> responseEntity = smsController.createSmsCode(PHONE_NUMBER);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(CODE);
    }
}