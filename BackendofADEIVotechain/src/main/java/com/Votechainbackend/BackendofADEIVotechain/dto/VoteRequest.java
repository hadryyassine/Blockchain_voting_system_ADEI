package com.Votechainbackend.BackendofADEIVotechain.dto;
import javax.validation.constraints.NotNull;

public class VoteRequest {
    @NotNull
    private Long candidateId;

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}

