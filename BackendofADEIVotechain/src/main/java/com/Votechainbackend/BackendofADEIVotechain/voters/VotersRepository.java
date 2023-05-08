package com.Votechainbackend.BackendofADEIVotechain.voters;
import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface VotersRepository extends JpaRepository<Voter, Long> {


        Optional<Voter> findByEmailAddress(String emailAddress);

        Optional<Voter> findById(Long id);

        List<Voter> findAll();

        Voter save(Voter voter);

    }

