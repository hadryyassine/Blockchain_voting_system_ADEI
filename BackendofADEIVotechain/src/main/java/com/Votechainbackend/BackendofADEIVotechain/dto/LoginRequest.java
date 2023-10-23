package com.Votechainbackend.BackendofADEIVotechain.dto;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String apogeecodeOrEmail;

    @NotBlank
    private String password;

    public String getApogeecodeOrEmail() {
        return apogeecodeOrEmail;
    }

    public void setApogeecodeOrEmail(String apogeecodeOrEmail) {
        this.apogeecodeOrEmail = apogeecodeOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
