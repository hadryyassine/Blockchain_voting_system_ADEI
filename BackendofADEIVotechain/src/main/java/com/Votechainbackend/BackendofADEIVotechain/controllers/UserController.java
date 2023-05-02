package com.Votechainbackend.BackendofADEIVotechain.controllers;

import com.Votechainbackend.BackendofADEIVotechain.models.User;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/add")
    User newUser(@RequestBody User newUser){
        userRepository.save(newUser);
        return newUser;
    }
}
