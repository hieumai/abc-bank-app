package com.voucher.provider.code.service.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = VoucherCodeController.class)
public class VoucherCodeControllerTest {
    @Autowired
    private VoucherCodeController voucherCodeController;

    @Test
    public void getCodeShouldReturnAGuidAfterARandomWaitTime() {
        Assertions.assertDoesNotThrow(() -> UUID.fromString(voucherCodeController.getCode()));
    }
}
