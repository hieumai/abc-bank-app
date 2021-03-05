package com.bank.abc.simdata.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationFacadeTest {
    @InjectMocks
    private AuthenticationFacade authenticationFacade;

    @Mock
    private Authentication mockAuthentication;

    @Test
    void getAuthenticationShouldReturnAuthenticationFromSecurityContextHolder() {
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
        assertThat(authenticationFacade.getAuthentication()).isEqualTo(mockAuthentication);
    }
}