package com.Votechainbackend.BackendofADEIVotechain.dto;

public class UserSummary {
    private Long id;
    private String codeapogee;
    private String name;

    public UserSummary(Long id, String codeapogee, String name) {
        this.id = id;
        this.codeapogee = codeapogee;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeapogee() {
        return codeapogee;
    }

    public void setCodeapogee(String codeapogee) {
        this.codeapogee = codeapogee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
