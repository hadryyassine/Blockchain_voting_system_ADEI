package com.Votechainbackend.BackendofADEIVotechain.entities;

import jakarta.persistence.*;

@Entity
public class Proposal {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName ="id")
    private User candidate;
    private String positionTitle;

    public void setPositionTitle(String postion) {
        this.positionTitle = postion;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

}
