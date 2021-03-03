package com.bank.abc.simdata.services;

import com.bank.abc.simdata.models.entities.User;
import com.bank.abc.simdata.models.entities.Voucher;
import com.bank.abc.simdata.repositories.UserRepository;
import com.bank.abc.simdata.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        User probeUser = new User();
        probeUser.setPhoneNumber(phoneNumber);
        Optional<User> user = userRepository.findOne(Example.of(probeUser));
        User resultUser = user.orElseGet(() -> userRepository.save(probeUser));
        Voucher newVoucher = new Voucher();
        newVoucher.setUser(resultUser);
        newVoucher.setCode(voucherCode);
        return voucherRepository.save(newVoucher);
    }
}
