package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.Committee;

import java.util.List;

public interface CommitteeService {

    Committee createCommittee(Committee committee);
    Committee findById(Long id);
    List<Committee> findAll();
}
