package com.Votechainbackend.BackendofADEIVotechain.dto;

import java.time.Instant;

public class UserProfile {
    private Long id;
    private String apogeecode;
    private String name;
    private Instant joinedAt;
    private Long electionCount;
    private Long voteCount;

    public UserProfile(Long id, String apogeecode, String name, Instant joinedAt, Long electionCount, Long voteCount) {
        this.id = id;
        this.apogeecode = apogeecode;
        this.name = name;
        this.joinedAt = joinedAt;
        this.electionCount = electionCount;
        this.voteCount = voteCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return apogeecode;
    }

    public void setUsername(String apogeecode) {
        this.apogeecode = apogeecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getPollCount() {
        return electionCount;
    }

    public void setPollCount(Long electionCount) {
        this.electionCount = electionCount;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
