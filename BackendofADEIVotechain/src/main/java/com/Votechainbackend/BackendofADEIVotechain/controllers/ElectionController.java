package com.example.VotingSystem.controllers;

import com.example.VotingSystem.entities.Election;
import com.example.VotingSystem.services.ElectionAlreadyExist;
import com.example.VotingSystem.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.DoubleToIntFunction;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ElectionController {

    @Autowired
    ElectionService electionService;

    @GetMapping("/elections")
    private List<Election> getAllElections(){
        return electionService.getAllElections();
    }

    @GetMapping("/election/{electionid}")
    private Election getElection(@PathVariable("electionid") long id){

        return electionService.getElectionById(id);
    }

    @DeleteMapping("/election/delete/{electionid}")
    private void deleteElection(@PathVariable("electionid") long id){

         electionService.delete(id);

    }
    @PostMapping("/elections/add")
    private void saveElection(@RequestBody Election election){
        electionService.save(election);
    }

    @PutMapping("/elections/update")
    private void updateElection(@RequestBody Election election) throws ElectionAlreadyExist {
        electionService.update(election);
        System.out.println("All good");
    }
}
