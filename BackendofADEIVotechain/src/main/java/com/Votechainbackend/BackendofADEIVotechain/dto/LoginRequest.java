package com.Votechainbackend.BackendofADEIVotechain.dto;



public class LoginRequest {
    private String emailAdress;



    private String password;


    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}