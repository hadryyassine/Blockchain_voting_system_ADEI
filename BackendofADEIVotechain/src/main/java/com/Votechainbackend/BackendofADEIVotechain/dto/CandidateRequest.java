package com.Votechainbackend.BackendofADEIVotechain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CandidateRequest {
    @NotBlank
    @Size(max = 40)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
