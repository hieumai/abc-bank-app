package com.bank.abc.simdata.security;

import com.bank.abc.simdata.models.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomUserDetailsTest {
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phoneNumber";
    private CustomUserDetails customUserDetails;

    @BeforeEach
    public void beforeEach() {
        User mockUser = new User();
        mockUser.setPassword(PASSWORD);
        mockUser.setPhoneNumber(PHONE_NUMBER);
        customUserDetails = new CustomUserDetails(mockUser);
    }

    @Test
    void getAuthoritiesShouldReturnSingletonListContainRoleUser() {
        assertThat(customUserDetails.getAuthorities()).isInstanceOf(List.class);
        List<?> listAuthorities = (List<?>) customUserDetails.getAuthorities();
        assertThat(listAuthorities).hasSize(1);
        assertThat(listAuthorities.get(0)).isInstanceOf(SimpleGrantedAuthority.class);
        SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority) listAuthorities.get(0);
        assertThat(simpleGrantedAuthority.getAuthority()).isEqualTo("user");
    }

    @Test
    void getPasswordShouldReturnPasswordOfUser() {
        assertThat(customUserDetails.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void getUsernameShouldReturnPhoneNumberOfUser() {
        assertThat(customUserDetails.getUsername()).isEqualTo(PHONE_NUMBER);
    }

    @Test
    void isAccountNonExpiredShouldReturnTrue() {
        assertThat(customUserDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    void isAccountNonLockedShouldReturnTrue() {
        assertThat(customUserDetails.isAccountNonLocked()).isTrue();
    }

    @Test
    void isCredentialsNonExpiredShouldReturnTrue() {
        assertThat(customUserDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void isEnabledShouldReturnTrue() {
        assertThat(customUserDetails.isEnabled()).isTrue();
    }
}