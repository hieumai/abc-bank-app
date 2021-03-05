package com.bank.abc.simdata.services;

import com.bank.abc.simdata.models.entities.User;
import com.bank.abc.simdata.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String SMS_VERIFICATION_CODE = "smsVerificationCode";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String ENCRYPTED_NEW_PASSWORD = "encryptedNewPassword";
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private SmsService mockSmsService;

    @Mock
    private BCryptPasswordEncoder mockPasswordEncoder;

    @Mock
    private User mockUser;

    @InjectMocks
    private UserService userService;

    @Test
    void resetPasswordShouldSaveUserWithNewPasswordToDBSuccessfully() {
        when(mockUserRepository.findOneByPhoneNumber(PHONE_NUMBER)).thenReturn(mockUser);
        when(mockSmsService.verifySmsVerificationCode(PHONE_NUMBER, SMS_VERIFICATION_CODE)).thenReturn(true);
        when(mockPasswordEncoder.encode(NEW_PASSWORD)).thenReturn(ENCRYPTED_NEW_PASSWORD);

        userService.resetPassword(PHONE_NUMBER, NEW_PASSWORD, SMS_VERIFICATION_CODE);
        verify(mockUser).setPassword(ENCRYPTED_NEW_PASSWORD);
        verify(mockUserRepository).save(mockUser);
    }

    @Test
    void resetPasswordShouldThrowResponseStatusExceptionWhenNoUserFoundWithPhoneNumber() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            when(mockUserRepository.findOneByPhoneNumber(PHONE_NUMBER)).thenReturn(null);
            userService.resetPassword(PHONE_NUMBER, NEW_PASSWORD, SMS_VERIFICATION_CODE);
        });
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("No user found with the input phone number");
    }

    @Test
    void resetPasswordShouldThrowResponseStatusExceptionWhenSmsVerificationReturnFalse() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            when(mockUserRepository.findOneByPhoneNumber(PHONE_NUMBER)).thenReturn(mockUser);
            when(mockSmsService.verifySmsVerificationCode(PHONE_NUMBER, SMS_VERIFICATION_CODE)).thenReturn(false);
            userService.resetPassword(PHONE_NUMBER, NEW_PASSWORD, SMS_VERIFICATION_CODE);
        });
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("Invalid phone number/verification code combination");
    }
}