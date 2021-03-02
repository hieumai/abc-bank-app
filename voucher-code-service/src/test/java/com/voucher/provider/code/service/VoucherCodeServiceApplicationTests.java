package com.voucher.provider.code.service;

import com.voucher.provider.code.service.controller.VoucherCodeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = VoucherCodeServiceApplication.class)
class VoucherCodeServiceApplicationTests {
	@Autowired
	private VoucherCodeController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
