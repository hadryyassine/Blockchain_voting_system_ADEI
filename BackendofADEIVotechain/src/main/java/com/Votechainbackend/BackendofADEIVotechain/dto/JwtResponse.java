package com.Votechainbackend.BackendofADEIVotechain.dto;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;


    private String name;
    private String emailAdress;
    private String apogeeCode;

    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String name, String emailAdress, String apogeeCode, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
        this.emailAdress = emailAdress;
        this.roles = roles;
    }



    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getApogeeCode() {
        return apogeeCode;
    }

    public void setApogeeCode(String apogeeCode) {
        this.apogeeCode = apogeeCode;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


}