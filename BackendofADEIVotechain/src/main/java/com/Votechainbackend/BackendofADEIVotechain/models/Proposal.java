package com.Votechainbackend.BackendofADEIVotechain.models;

import jakarta.persistence.*;

@Entity
public class Proposal {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User candidate;
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

}
