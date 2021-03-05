package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.models.entities.Voucher;
import com.bank.abc.simdata.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<String>> getAllVoucherCodeByUserPhoneNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(voucherService.getAllVouchersByUserPhoneNumber(phoneNumber).stream()
                .map(Voucher::getCode).collect(Collectors.toList()));
    }
}
