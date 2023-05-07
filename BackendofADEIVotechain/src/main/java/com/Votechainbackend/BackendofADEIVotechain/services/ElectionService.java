package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.Election;

import java.util.List;

public interface ElectionService {
    Election createElection(Election election);
    Election findById(Long id);
    List<Election> findAll();
    Election updateElection(Election election);
    void deleteElection(Long id);
    List<Election> findByYear(int year);
}
