package com.bank.abc.simdata.models;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.google.code.beanmatchers.BeanMatchers.isABeanWithValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

class ApiErrorTest {
    @Test
    public void apiErrorShouldHasValidGettersSetters() {
        assertThat(newApiError(), isABeanWithValidGettersAndSetters());
    }

    @Test
    public void apiErrorShouldBeCreatedSuccessfully() {
        assertThat(newApiError(), is(notNullValue()));
    }

    private ApiError newApiError() {
        return new ApiError(HttpStatus.BAD_REQUEST, "message", "error");
    }
}