package com.bank.abc.simdata.services;

import com.bank.abc.simdata.models.entities.User;
import com.bank.abc.simdata.models.entities.Voucher;
import com.bank.abc.simdata.repositories.UserRepository;
import com.bank.abc.simdata.repositories.VoucherRepository;
import com.bank.abc.simdata.security.AuthenticationFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String VOUCHER_CODE = "voucherCode";
    @Mock
    private VoucherRepository mockVoucherRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AuthenticationFacade mockAuthenticationFacade;

    @Mock
    private List<Voucher> mockVouchers;

    @Mock
    private User mockUser;

    @Mock
    private Voucher mockVoucher;

    @Captor
    private ArgumentCaptor<Voucher> voucherArgumentCaptor;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @InjectMocks
    private VoucherService voucherService;

    @Test
    void getAllVouchersByUserPhoneNumberShouldReturnResultFromRepository() {
        when(mockAuthenticationFacade.getAuthentication().getName()).thenReturn(PHONE_NUMBER);
        when(mockVoucherRepository.getAllByUserPhoneNumber(PHONE_NUMBER)).thenReturn(mockVouchers);
        assertThat(voucherService.getAllVouchersByUserPhoneNumber(PHONE_NUMBER)).isEqualTo(mockVouchers);
    }

    @Test
    void getAllVouchersByUserPhoneNumberShouldThrowExceptionWhenCurrentUserIsNotMatchInput() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            when(mockAuthenticationFacade.getAuthentication().getName()).thenReturn(PHONE_NUMBER);
            voucherService.getAllVouchersByUserPhoneNumber("differentPhoneNumber");
        });
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(exception.getReason()).isEqualTo("You don't have permission to access this resource");
    }

    @Test
    void saveVoucherByUserPhoneNumberShouldAddVoucherForExistingUser() {
        when(mockUserRepository.findOneByPhoneNumber(PHONE_NUMBER)).thenReturn(mockUser);
        when(mockVoucherRepository.save(any(Voucher.class))).thenReturn(mockVoucher);

        Voucher createdVoucher = voucherService.saveVoucherByUserPhoneNumber(PHONE_NUMBER, VOUCHER_CODE);
        assertThat(createdVoucher).isEqualTo(mockVoucher);
        verify(mockUserRepository, times(0)).save(any(User.class));
        verify(mockVoucherRepository).save(voucherArgumentCaptor.capture());
        Voucher newVoucher = voucherArgumentCaptor.getValue();
        assertThat(newVoucher.getUser()).isEqualTo(mockUser);
        assertThat(newVoucher.getCode()).isEqualTo(VOUCHER_CODE);
    }

    @Test
    void saveVoucherByUserPhoneNumberShouldCreateNewUserThenAddVoucherForNewUser() {
        when(mockUserRepository.findOneByPhoneNumber(PHONE_NUMBER)).thenReturn(null);
        when(mockVoucherRepository.save(any(Voucher.class))).thenReturn(mockVoucher);
        when(mockUserRepository.save(any(User.class))).thenReturn(mockUser);

        Voucher createdVoucher = voucherService.saveVoucherByUserPhoneNumber(PHONE_NUMBER, VOUCHER_CODE);
        assertThat(createdVoucher).isEqualTo(mockVoucher);
        verify(mockUserRepository).save(userArgumentCaptor.capture());
        verify(mockVoucherRepository).save(voucherArgumentCaptor.capture());
        User newUser = userArgumentCaptor.getValue();
        assertThat(newUser.getPhoneNumber()).isEqualTo(PHONE_NUMBER);

        Voucher newVoucher = voucherArgumentCaptor.getValue();
        assertThat(newVoucher.getUser()).isEqualTo(mockUser);
        assertThat(newVoucher.getCode()).isEqualTo(VOUCHER_CODE);
    }
}