package com.Votechainbackend.BackendofADEIVotechain.controllers;


import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")

@RestController
public class CandidateController {
    @Autowired
    CandidateService candidateService;
    @GetMapping("/candidates")
    private List<Candidate> getAll(){
        return candidateService.getCandidates();
    }
    @GetMapping("/candidates/{candidateId}")
    private Optional<Candidate> getCandidate(@PathVariable("candidateId")long id){
        return candidateService.getCandiadate(id);
    }
    @PostMapping("/candidate/add")
    private void addCandidate(@RequestBody Candidate candidate){
        candidateService.saveOrUpdate(candidate);
    }
    @PutMapping("/candidate/update")
    private void updateCandidate(@RequestBody Candidate candidate){
        candidateService.saveOrUpdate(candidate);
    }
    @DeleteMapping("/candidate/delete/{id}")
    private void deleteCandidate(@PathVariable("id") long id)
    {
        candidateService.delete(id);
    }
}
