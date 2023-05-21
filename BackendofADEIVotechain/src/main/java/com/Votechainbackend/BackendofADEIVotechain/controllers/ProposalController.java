package com.Votechainbackend.BackendofADEIVotechain.controllers;


import com.Votechainbackend.BackendofADEIVotechain.entities.Proposal;
import com.Votechainbackend.BackendofADEIVotechain.services.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
public class ProposalController {
    @Autowired
    ProposalService proposalService;
    @GetMapping( "/candidate/{user_id}/proposal")
    public Optional<Proposal> getProposal(@PathVariable("user_id") Long id) {
        return proposalService.getCandidateProposal(id);
    }
    @PostMapping("/candidate/{candidate_id}/submitProposal")
    public void submitProposal(@RequestBody Proposal proposal,@PathVariable("candidate_id") Long id){
        proposalService.saveOrUpdate(proposal,id);
    }
}
