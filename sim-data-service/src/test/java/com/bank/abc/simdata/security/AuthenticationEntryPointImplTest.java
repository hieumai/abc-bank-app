package com.bank.abc.simdata.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationEntryPointImplTest {
    @InjectMocks
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Test
    void afterPropertiesSetShouldSetTheRealmName() {
        authenticationEntryPoint.afterPropertiesSet();
        assertThat(authenticationEntryPoint.getRealmName()).isEqualTo("abc");
    }
}