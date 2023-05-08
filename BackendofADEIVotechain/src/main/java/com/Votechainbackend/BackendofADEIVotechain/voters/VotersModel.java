package com.Votechainbackend.BackendofADEIVotechain.voters;
import com.Votechainbackend.BackendofADEIVotechain.entities.Role;



//I used the Voter class from the entities package to create the VotersModel class in the voters package

public class VotersModel {
    private Long id;

    private String firstName;
    private String lastName;
    private String address;
    private String publicKey;
    private String privateKey;

    private String apogeeCode;
    private Role role;

    public VotersModel(Long id,
                       String firstName,
                       String lastName,
                       String address,
                       String publicKey,
                       String privateKey,
                       String apogeeCode,
                       Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.apogeeCode = apogeeCode;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getApogeeCode() {
        return apogeeCode;
    }

    public void setApogeeCode(String apogeeCode) {
        this.apogeeCode = apogeeCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}