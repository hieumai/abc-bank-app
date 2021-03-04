package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @PatchMapping("/resetPassword")
    public void resetPassword(@RequestParam String phoneNumber, @RequestParam String newPassword,
                              @RequestParam String verificationCode, HttpServletResponse response) {
        userService.resetPassword(phoneNumber, newPassword, verificationCode);
        response.setStatus(HttpStatus.ACCEPTED.value());
    }
}
