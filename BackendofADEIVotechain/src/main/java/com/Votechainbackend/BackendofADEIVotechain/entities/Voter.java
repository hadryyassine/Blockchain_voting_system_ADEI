package com.Votechainbackend.BackendofADEIVotechain.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Voter")

public class Voter extends user {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    protected Voter() {

    }

    public Voter(Long id, String name, String emailAddress, String apogeeCode, String password, Role role, Long id1) {
        super(id, name, emailAddress, apogeeCode, password, role);
        this.id = id1;
    }
}
