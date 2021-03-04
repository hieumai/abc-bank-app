package com.bank.abc.simdata.services;

import com.bank.abc.simdata.models.entities.User;
import com.bank.abc.simdata.repositories.UserRepository;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void resetPassword(String phoneNumber, String newPassword, String smsVerificationCode) {
        User user = userRepository.findOneByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found with the input phone number");
        }
        if (!smsService.verifySmsVerificationCode(phoneNumber, smsVerificationCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number/verification code combination");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
