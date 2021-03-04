package com.bank.abc.simdata.services;

import com.bank.abc.simdata.models.entities.User;
import com.bank.abc.simdata.models.entities.Voucher;
import com.bank.abc.simdata.repositories.UserRepository;
import com.bank.abc.simdata.repositories.VoucherRepository;
import com.bank.abc.simdata.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    public List<Voucher> getAllVouchersByUserPhoneNumber(String phoneNumber) {
        if (!authenticationFacade.getAuthentication().getName().equals(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access this resource");
        }
        return voucherRepository.getAllByUserPhoneNumber(phoneNumber);
    }

    public Voucher saveVoucherByUserPhoneNumber(String phoneNumber, String voucherCode) {
        User user = userRepository.findOneByPhoneNumber(phoneNumber);
        if (user == null) {
            User newUser = new User();
            newUser.setPhoneNumber(phoneNumber);
            user = userRepository.save(newUser);
        }
        Voucher newVoucher = new Voucher();
        newVoucher.setUser(user);
        newVoucher.setCode(voucherCode);
        return voucherRepository.save(newVoucher);
    }
}
