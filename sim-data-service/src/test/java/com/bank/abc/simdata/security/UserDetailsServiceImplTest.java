package com.bank.abc.simdata.security;

import com.bank.abc.simdata.models.entities.User;
import com.bank.abc.simdata.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    public static final String USERNAME = "username";
    public static final String PHONE_NUMBER = "phoneNumber";

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private User mockUser;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameShouldReturnCustomUserDetailsForFoundUser() {
        when(mockUser.getPhoneNumber()).thenReturn(PHONE_NUMBER);
        when(mockUserRepository.findOneByPhoneNumber(USERNAME)).thenReturn(mockUser);
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(PHONE_NUMBER);
    }

    @Test
    void loadUserByUsernameShouldThrowExceptionWhenNoUserIsFound() {
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            when(mockUserRepository.findOneByPhoneNumber(USERNAME)).thenReturn(null);
            userDetailsService.loadUserByUsername(USERNAME);
        });
        assertThat(exception.getMessage()).isEqualTo("Could not find user");
    }
}