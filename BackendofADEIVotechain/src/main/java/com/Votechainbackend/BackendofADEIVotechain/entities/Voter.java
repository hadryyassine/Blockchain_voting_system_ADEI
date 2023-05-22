package com.Votechainbackend.BackendofADEIVotechain.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voters")
public class Voter extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "voter_voted_positions", joinColumns = @JoinColumn(name = "voter_id"))
    @Column(name = "position_title")
    private List<String> votedPositions;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getVotedPositions() {
        if (votedPositions == null) {
            votedPositions = new ArrayList<>();
        }
        return votedPositions;
    }

    public void setVotedPositions(List<String> votedPositions) {
        this.votedPositions = votedPositions;
    }

    public void addVotedPosition(String positionTitle) {
        getVotedPositions().add(positionTitle);
    }

    public boolean hasVotedForPosition(String positionTitle) {
        return getVotedPositions().contains(positionTitle);
    }
}
