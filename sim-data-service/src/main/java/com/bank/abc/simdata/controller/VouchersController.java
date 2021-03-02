package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.model.Voucher;
import com.bank.abc.simdata.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vouchers")
public class VouchersController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping
    @ResponseBody
    public List<String> getAllVoucherCodeByUserPhoneNumber(@RequestParam String userPhoneNumber) {
        return voucherService.getAllVouchersByUserPhoneNumber(userPhoneNumber).stream().map(Voucher::getCode).collect(Collectors.toList());
    }
}
