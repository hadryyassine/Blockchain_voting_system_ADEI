package com.Votechainbackend.BackendofADEIVotechain.controllers;

import com.Votechainbackend.BackendofADEIVotechain.models.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidtes")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Candidate> candidtes(){return candidateRepository.findAll();}

}
