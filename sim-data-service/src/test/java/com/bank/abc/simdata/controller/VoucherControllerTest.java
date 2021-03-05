package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.models.entities.Voucher;
import com.bank.abc.simdata.services.VoucherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoucherControllerTest {
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String CODE_1 = "code1";
    public static final String CODE_2 = "code2";
    @Mock
    private VoucherService mockVoucherService;

    @InjectMocks
    private VoucherController voucherController;

    @Test
    void getAllVoucherCodeByUserPhoneNumberShouldReturnListOfCodeFromListOfVouchersFromVoucherService() {
        Voucher voucher1 = new Voucher();
        voucher1.setCode(CODE_1);
        Voucher voucher2 = new Voucher();
        voucher2.setCode(CODE_2);
        when(mockVoucherService.getAllVouchersByUserPhoneNumber(PHONE_NUMBER)).thenReturn(asList(voucher1, voucher2));

        ResponseEntity<List<String>> responseEntity = voucherController.getAllVoucherCodeByUserPhoneNumber(PHONE_NUMBER);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(2);
        assertThat(responseEntity.getBody().get(0)).isEqualTo(CODE_1);
        assertThat(responseEntity.getBody().get(1)).isEqualTo(CODE_2);
    }
}