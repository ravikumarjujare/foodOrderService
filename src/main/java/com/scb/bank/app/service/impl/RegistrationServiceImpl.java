package com.scb.bank.app.service.impl;

import com.scb.bank.app.entity.Account;
import com.scb.bank.app.entity.User;
import com.scb.bank.app.meta.ResponseMessageType;
import com.scb.bank.app.repository.UserRepository;
import com.scb.bank.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String registerUser(User user) {
        Account account = user.getAccount();
        account.setUser(user);
        Optional<User> userDetails = userRepository.findByPanNumber(user.getPanNumber());
        if (userDetails.isPresent()) {
            return ResponseMessageType.USER_ALREADY_EXIST.getType();
        } else {
            userRepository.save(user);
            return String.format("%s: %s",ResponseMessageType.USER_REGISTRATION.getType(),userRepository.findByPanNumber(user.getPanNumber()).get().getAccount().getId());
        }
    }

}
