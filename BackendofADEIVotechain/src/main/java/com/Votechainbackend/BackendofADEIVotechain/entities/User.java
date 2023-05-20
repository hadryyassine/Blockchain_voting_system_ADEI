package com.Votechainbackend.BackendofADEIVotechain.entities;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "name")
    private String username;
    @Column(name = "email")
    private String emailAddress;
    @Column(name = "apogee_Code")

    private String apogeeCode;

    @Column(name = "password")
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleE> roles = new HashSet<>();

    public User() {

    }




    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getName() {
        return username;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getApogeeCode() {
        return apogeeCode;
    }

    public void setApogeeCode(String apogeeCode) {
        this.apogeeCode = apogeeCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleE> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleE> roles) {
        this.roles = roles;
    }



    public User(String username, String emailAddress, String apogeeCode, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.apogeeCode = apogeeCode;
        this.password = password;
    }



}