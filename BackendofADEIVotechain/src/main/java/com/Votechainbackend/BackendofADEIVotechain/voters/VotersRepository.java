package com.Votechainbackend.BackendofADEIVotechain.voters;

import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotersRepository extends JpaRepository<Voter, Long> {

    Optional<Voter> findByEmailAddressIgnoreCase(String emailAddress);

}
