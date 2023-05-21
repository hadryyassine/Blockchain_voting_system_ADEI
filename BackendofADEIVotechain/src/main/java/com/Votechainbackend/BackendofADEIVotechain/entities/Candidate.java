package com.Votechainbackend.BackendofADEIVotechain.entities;



import javax.persistence.*;

@Entity
@Table(name = "candidates")

public class Candidate extends User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nbrVotes")
    private int nbrVotes;

    @Column(name = "positionTitle")
    private String positionTitle;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    // Getters and setters

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public int getNbrVotes() {
        return nbrVotes;
    }

    public void setNbrVotes(int nbrVotes) {
        this.nbrVotes = nbrVotes;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    protected Candidate() {
    }
    public Candidate(String name, String emailAddress, String apogeeCode, String password) {
        super(name, emailAddress, apogeeCode, password);
    }
}
