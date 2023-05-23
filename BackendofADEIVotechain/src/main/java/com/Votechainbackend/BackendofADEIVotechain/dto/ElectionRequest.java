package com.Votechainbackend.BackendofADEIVotechain.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ElectionRequest {
    @NotBlank
    @Size(max = 140)
    private String positiontitle;

    @NotNull
    @Size(min = 2, max = 6)
    @Valid
    private List<CandidateRequest> candidates;

    @NotNull
    @Valid
    private ElectionLength electionLength;

    public String getPositiontitle() {
        return positiontitle;
    }

    public void setPositiontitle(String positiontitle) {
        this.positiontitle = positiontitle;
    }

    public List<CandidateRequest> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateRequest> candidates) {
        this.candidates = candidates;
    }

    public ElectionLength getElectionLength() {
        return electionLength;
    }

    public void setElectionLength(ElectionLength electionLength) {
        this.electionLength = electionLength;
    }
}
