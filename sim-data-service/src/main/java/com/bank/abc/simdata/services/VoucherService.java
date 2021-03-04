package com.bank.abc.simdata.services;

import com.bank.abc.simdata.models.entities.User;
import com.bank.abc.simdata.models.entities.Voucher;
import com.bank.abc.simdata.repositories.UserRepository;
import com.bank.abc.simdata.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Voucher> getAllVouchersByUserPhoneNumber(String phoneNumber) {
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
