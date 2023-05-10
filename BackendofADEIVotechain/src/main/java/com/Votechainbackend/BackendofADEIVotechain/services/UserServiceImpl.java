package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class UserServiceImpl implements UserService , CustomUserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User CreatUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isEmailInUse(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).isPresent();
    }

    @Override
    public boolean isApogeeCodeExist(String apogeeCode) {
        return false;
    }

    @Override
    public User findByEmail(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).orElse(null);
    }

    @Override
    public User registerUser(User user) {


        if (isEmailInUse(user.getEmailAddress())) {
            throw new IllegalStateException("Email address is already in use.");
        }

        // Perform other necessary validations

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (user.getEmailAddress() == null || user.getEmailAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = emailPattern.matcher(user.getEmailAddress());

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Email address is not valid.");
        }

        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher passmatcher = passwordPattern.matcher(user.getPassword());

        if (!passmatcher.matches()) {
            throw new IllegalArgumentException("Password must have at least 8 characters, including upper and lower case letters, numbers, and special characters.");
        }




        // Encode the password before saving the user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + emailAddress));

        return new CustomUserDetails(user);
    }





}


