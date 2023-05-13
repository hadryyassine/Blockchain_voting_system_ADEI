package com.Votechainbackend.BackendofADEIVotechain.controllers;


import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.entities.Proposal;
import com.Votechainbackend.BackendofADEIVotechain.repositories.CandidateRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/proposal")
public class ProposalController {
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @GetMapping(path = "/{user_id}")
    public Optional<Proposal> getProposal(@PathVariable("user_id") Long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        return proposalRepository.findByCandidate(candidate);
    }
}
