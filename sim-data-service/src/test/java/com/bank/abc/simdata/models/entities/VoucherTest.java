package com.bank.abc.simdata.models.entities;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

class VoucherTest {
    @Test
    public void userShouldHasValidGettersSetters() {
        assertThat(Voucher.class, hasValidGettersAndSetters());
    }

    @Test
    public void userShouldHaveANoArgsConstructor() {
        assertThat(Voucher.class, hasValidBeanConstructor());
    }
}