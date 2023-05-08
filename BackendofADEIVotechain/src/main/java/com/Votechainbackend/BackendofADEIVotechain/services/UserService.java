package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.Committee;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;

import java.util.List;

public interface UserService {
    User CreatUser(User user);
    User findById(Long id);
    List<User> findAll();
    User registerUser(User user);
    User findByEmailAddress(String emailAddress);
}
