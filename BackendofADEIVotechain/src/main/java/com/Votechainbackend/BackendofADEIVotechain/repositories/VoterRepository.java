package com.Votechainbackend.BackendofADEIVotechain.repositories;
import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VoterRepository extends JpaRepository<Voter,Long> {
}
