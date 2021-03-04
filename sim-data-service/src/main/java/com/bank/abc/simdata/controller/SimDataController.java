package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.services.SimDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/data")
public class SimDataController {
    @Autowired
    private SimDataService simDataService;

    @PostMapping
    public ResponseEntity<String> buyData(@RequestParam String phoneNumber) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(simDataService.buyData(phoneNumber));
    }

}
