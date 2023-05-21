package com.Votechainbackend.BackendofADEIVotechain.entities;


import javax.persistence.*;

@Entity
@Table(name = "Voter")

public class Voter extends User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    public Voter() {

    }

    public Voter(String username, String emailAddress, String apogeeCode, String password) {
        super(username, emailAddress, apogeeCode, password);

    }
}