package com.bank.abc.simdata.models;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.isABeanWithValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

class ExecutorResultTest {
    @Test
    public void apiErrorShouldHasValidGettersSetters() {
        assertThat(new ExecutorResult("phoneNumber"), isABeanWithValidGettersAndSetters());
    }
}