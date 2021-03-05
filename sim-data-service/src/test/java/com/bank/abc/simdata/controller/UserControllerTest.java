package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UserController userController;

    @Mock
    private HttpServletResponse mockResponse;

    @Test
    void resetPasswordShouldReturnHttpStatusAcceptedIfResetPasswordIsSuccessful() {
        userController.resetPassword("phoneNumber", "newPassword", "verificationCode", mockResponse);
        verify(mockResponse).setStatus(HttpStatus.ACCEPTED.value());
    }
}