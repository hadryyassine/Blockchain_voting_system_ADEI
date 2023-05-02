package com.Votechainbackend.BackendofADEIVotechain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Candidate extends User{
    private final int rolId = 1;
    private int numberOfVotes;
    private String position;
    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "numberOfVotes=" + numberOfVotes +
                ", position='" + position + '\'' +
                '}';
    }
}
