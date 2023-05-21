package com.Votechainbackend.BackendofADEIVotechain.services;

import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.entities.Proposal;
import com.Votechainbackend.BackendofADEIVotechain.repositories.CandidateRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    public void saveOrUpdate(Proposal proposal, Long id){
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        proposal.setCandidate(candidate);
        proposalRepository.save(proposal);
    }
    public Optional<Proposal> getCandidateProposal(Long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        return proposalRepository.findByCandidate(candidate);
    }
}
