package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;

import java.util.List;

public interface VoterService {
    Voter createVoter(Voter voter);
    Voter findById(Long id);
    List<Voter> findAll();
}
