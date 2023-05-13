package com.Votechainbackend.BackendofADEIVotechain.controllers;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return registrationService.register(user);
    }
}

