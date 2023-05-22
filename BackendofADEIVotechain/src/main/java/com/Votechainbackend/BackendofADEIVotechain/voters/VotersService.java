package com.Votechainbackend.BackendofADEIVotechain.voters;

import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import com.Votechainbackend.BackendofADEIVotechain.repositories.CandidateRepository;
import com.Votechainbackend.BackendofADEIVotechain.voters.VotersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotersService {

    private final VotersRepository votersRepository;
    private final CandidateRepository candidateRepository;

    @Autowired
    public VotersService(VotersRepository votersRepository, CandidateRepository candidateRepository) {
        this.votersRepository = votersRepository;
        this.candidateRepository = candidateRepository;
    }

    public Voter createVoter(Voter voter) {
        return votersRepository.save(voter);
    }

    public Optional<Voter> getVoterById(Long id) {
        return votersRepository.findById(id);
    }

    public Optional<Voter> updateVoter(Long id, Voter voter) {
        Optional<Voter> existingVoter = votersRepository.findById(id);
        if (existingVoter.isPresent()) {
            Voter updatedVoter = existingVoter.get();
            updatedVoter.setName(voter.getName());
            updatedVoter.setEmailAddress(voter.getEmailAddress());
            updatedVoter.setApogeeCode(voter.getApogeeCode());
            updatedVoter.setPassword(voter.getPassword());
            return Optional.of(votersRepository.save(updatedVoter));
        } else {
            return Optional.empty();
        }
    }

    public void deleteVoter(Long id) {
        votersRepository.deleteById(id);
    }

    public void vote(Long voterId, String positionTitle) {
        Voter voter = votersRepository.findById(voterId).orElseThrow(() -> new IllegalArgumentException("Invalid voter ID"));

        if (voter.hasVotedForPosition(positionTitle)) {
            throw new IllegalArgumentException("Voter has already voted for the position.");
        }

        Candidate candidate = candidateRepository.findByPositionTitle(positionTitle).orElseThrow(() -> new IllegalArgumentException("Invalid position title."));

        candidate.setNbrVotes(candidate.getNbrVotes() + 1);

        voter.addVotedPosition(positionTitle);

        votersRepository.save(voter);
        candidateRepository.save(candidate);
    }

    public List<Candidate> getVotes() {
        return candidateRepository.findAll();
    }
}
