package com.Votechainbackend.BackendofADEIVotechain.entities;

import com.Votechainbackend.BackendofADEIVotechain.entities.audit.UserDateAudit;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "election")
public class Election extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 140)
    private String positiontitle;

    @OneToMany(
            mappedBy = "election",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Candidate> candidates = new ArrayList<>();

    @NotNull
    private Instant expirationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositiontitle() {
        return positiontitle;
    }

    public void setPositiontitle(String positiontitle) {
        this.positiontitle = positiontitle;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidate) {
        this.candidates = candidate;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
        candidate.setElection(this);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
        candidate.setElection(null);
    }
}
