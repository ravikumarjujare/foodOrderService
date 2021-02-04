package com.scb.bank.app.controller;

import com.scb.bank.app.entity.User;
import com.scb.bank.app.service.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @PostMapping("register")
    public String registerUser(@Valid @RequestBody User user) {
        return registrationServiceImpl.registerUser(user);
    }

}
