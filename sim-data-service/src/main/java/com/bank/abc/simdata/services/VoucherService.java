package com.bank.abc.simdata.services;

import com.bank.abc.simdata.model.Voucher;
import com.bank.abc.simdata.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> getAllVouchersByUserPhoneNumber(String phoneNumber) {
        return voucherRepository.getAllByUserPhoneNumber(phoneNumber);
    }
}
