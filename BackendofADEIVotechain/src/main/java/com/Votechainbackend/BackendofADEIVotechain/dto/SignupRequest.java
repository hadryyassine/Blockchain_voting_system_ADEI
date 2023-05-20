package com.Votechainbackend.BackendofADEIVotechain.dto;


import java.util.Set;


public class SignupRequest {

    private String emailAdress;

    private Set<String> role;


    private String password;
    private String apogeeCode;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApogeeCode() {
        return apogeeCode;
    }

    public void setApogeeCode(String apogeeCode) {
        this.apogeeCode = apogeeCode;
    }





}