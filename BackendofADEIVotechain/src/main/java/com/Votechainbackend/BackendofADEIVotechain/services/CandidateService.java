package com.Votechainbackend.BackendofADEIVotechain.services;



import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    public List<Candidate> getCandidates(){
        List<Candidate> candidates = new ArrayList<Candidate>();
        candidateRepository.findAll().forEach(candidate->candidates.add(candidate));
        return candidates;
    }
    public Optional<Candidate> getCandiadate(long id){
        return candidateRepository.findById(id);
    }
    public void saveOrUpdate(Candidate candidate){
        candidateRepository.save(candidate);
    }
    public void delete(long id)
    {
        candidateRepository.deleteById(id);
    }

}
