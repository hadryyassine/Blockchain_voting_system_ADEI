package com.Votechainbackend.BackendofADEIVotechain.entities;

public class CandidateVoteCount {
    private Long candidateId;
    private Long voteCount;

    public CandidateVoteCount(Long candidateId, Long voteCount) {
        this.candidateId = candidateId;
        this.voteCount = voteCount;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}

