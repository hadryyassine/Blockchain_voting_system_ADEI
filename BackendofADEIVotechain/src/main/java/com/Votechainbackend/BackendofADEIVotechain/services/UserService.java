package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface UserService {
    User CreatUser(User user);
    User findById(Long id);
    List<User> findAll();

    boolean isEmailInUse(String emailAddress);
    boolean isApogeeCodeExist(String apogeeCode);


    User findByEmail(String email);
}
