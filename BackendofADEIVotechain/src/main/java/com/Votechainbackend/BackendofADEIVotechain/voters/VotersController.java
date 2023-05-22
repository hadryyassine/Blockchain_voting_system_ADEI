package com.Votechainbackend.BackendofADEIVotechain.voters;

import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/voters")
public class VotersController {

    private final VotersService voterService;

    @Autowired
    public VotersController(VotersService voterService) {
        this.voterService = voterService;
    }

    @PostMapping
    public ResponseEntity<Voter> createVoter(@RequestBody Voter voter) {
        Voter createdVoter = voterService.createVoter(voter);
        return ResponseEntity.created(URI.create("/api/voters/" + createdVoter.getId())).body(createdVoter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable Long id) {
        Optional<Voter> voter = voterService.getVoterById(id);
        return voter.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voter> updateVoter(@PathVariable Long id, @RequestBody Voter voter) {
        Optional<Voter> updatedVoter = voterService.updateVoter(id, voter);
        return updatedVoter.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoter(@PathVariable Long id) {
        voterService.deleteVoter(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{voterId}/vote/{positionTitle}")
    public void addVote(@PathVariable("voterId") Long voterId, @PathVariable("positionTitle") String positionTitle) {
        voterService.vote(voterId, positionTitle);
    }

    @GetMapping("/votes")
    public List<Candidate> getVotes() {
        return voterService.getVotes();
    }
}
