package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        // Check if user already exists
        Optional<User> userOptional = userRepository.findByEmailAddress(user.getEmailAddress());
        if(userOptional.isPresent()){
            throw new IllegalStateException("Email already in use!");
        }
        return userRepository.save(user);
    }
}


