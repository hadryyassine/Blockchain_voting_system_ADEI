package com.example.VotingSystem.services;


import com.example.VotingSystem.entities.Election;
import com.example.VotingSystem.repositories.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElectionService {

    @Autowired
    ElectionRepository electionRepository;

    public List<Election> getAllElections(){
        List<Election> election = new ArrayList<Election>();
        electionRepository.findAll().forEach(election1 -> election.add(election1));
        return election;
    }
    public Election getElectionById(long id){
        return electionRepository.findById(id).get();
    }

    public void save(Election election){
        Date now = new Date();
        if (now.after(election.getStartDate())){
            election.setOpen(false);
        }
        electionRepository.save(election);
    }
    public void update(Election election) throws ElectionAlreadyExist {

        if(election.isOpen()){
            electionRepository.save(election);}
        else {
            throw new ElectionAlreadyExist("Election deja faite !");
        }
        }

    public void delete(long id){
        electionRepository.deleteById(id);
    }

}
