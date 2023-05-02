package com.Votechainbackend.BackendofADEIVotechain.repositories;

import com.Votechainbackend.BackendofADEIVotechain.models.Candidate;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Integer> {
}
