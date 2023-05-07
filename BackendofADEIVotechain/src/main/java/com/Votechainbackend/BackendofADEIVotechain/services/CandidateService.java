package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;

import java.util.List;

public interface CandidateService  {

    Candidate createCandidate(Candidate candidate);
    Candidate findById(Long id);
    List<Candidate> findAll();
    List<Candidate> findByElectionId(Long electionId);
}
