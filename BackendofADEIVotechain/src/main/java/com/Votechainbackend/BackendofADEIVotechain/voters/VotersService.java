package com.Votechainbackend.BackendofADEIVotechain.voters;
import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Votechainbackend.BackendofADEIVotechain.voters.VotersRepository;
import java.util.Optional;

@Service
public class VotersService {

    private final VotersRepository voterRepository;

    @Autowired
    public VotersService(VotersRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public Voter createVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    public Optional<Voter> getVoterById(Long id) {
        return voterRepository.findById(id);
    }


    public Optional<Voter> updateVoter(Long id, Voter voter) {
        Optional<Voter> existingVoter = voterRepository.findById(id);
        if (existingVoter.isPresent()) {
            Voter updatedVoter = existingVoter.get();
            updatedVoter.setName(voter.getName());
            updatedVoter.setEmailAddress(voter.getEmailAddress());
            updatedVoter.setApogeeCode(voter.getApogeeCode());
            updatedVoter.setPassword(voter.getPassword());
            return Optional.of(voterRepository.save(updatedVoter));
        } else {
            return Optional.empty();
        }
    }

    public void deleteVoter(Long id) {
        voterRepository.deleteById(id);
    }
}


