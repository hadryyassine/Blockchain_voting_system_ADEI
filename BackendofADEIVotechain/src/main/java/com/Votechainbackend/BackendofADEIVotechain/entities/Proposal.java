package com.Votechainbackend.BackendofADEIVotechain.entities;



import javax.persistence.*;

@Entity
public class Proposal {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName ="id")
    private Candidate candidate;
    private String positionTitle;
    private String CIN;
    private boolean status = false;


    public Proposal() {
    }

    public void setPositionTitle(String postion) {
        this.positionTitle = postion;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getCIN() {
        return CIN;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

}
