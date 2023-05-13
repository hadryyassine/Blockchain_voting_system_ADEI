package com.Votechainbackend.BackendofADEIVotechain.entities;


import jakarta.persistence.*;

@Table(name = "committees")
public class Committee extends User {

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
    protected Committee() {
    }

    public Committee(Long id, String name, String emailAddress, String apogeeCode, String password, Role role, Long id1) {
        super(id, name, emailAddress, apogeeCode, password, role);
        this.id = id1;
    }

}

