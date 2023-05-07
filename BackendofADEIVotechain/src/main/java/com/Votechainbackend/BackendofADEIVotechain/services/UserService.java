package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;

public interface UserService {
    User registerUser(User user);
    User findByEmailAddress(String emailAddress);
}
