package com.Votechainbackend.BackendofADEIVotechain.dto;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;

public class LoginRequest {

    public User user;
    private String password;

    // Getters and setters
    public String getEmailAddress() {
        return user.getEmailAddress();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
