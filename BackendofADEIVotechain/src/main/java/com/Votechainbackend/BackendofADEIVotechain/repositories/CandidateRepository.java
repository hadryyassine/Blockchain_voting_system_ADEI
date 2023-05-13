package com.Votechainbackend.BackendofADEIVotechain.repositories;


import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Long> {

}
