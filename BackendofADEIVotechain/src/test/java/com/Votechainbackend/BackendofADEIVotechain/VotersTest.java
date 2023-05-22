package com.Votechainbackend.BackendofADEIVotechain;

import com.Votechainbackend.BackendofADEIVotechain.entities.Candidate;
import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import com.Votechainbackend.BackendofADEIVotechain.voters.VotersRepository;
import com.Votechainbackend.BackendofADEIVotechain.voters.VotersService;
import com.Votechainbackend.BackendofADEIVotechain.repositories.CandidateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VotersTest {

    @Mock
    private VotersRepository votersRepository;

    @InjectMocks
    private VotersService votersService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateVoter() {
        Voter voter = new Voter();
        voter.setId(1L);
        voter.setName("John Doe");

        when(votersRepository.save(any(Voter.class))).thenReturn(voter);

        Voter createdVoter = votersService.createVoter(voter);

        Assertions.assertNotNull(createdVoter);
        Assertions.assertEquals(voter.getId(), createdVoter.getId());
        Assertions.assertEquals(voter.getName(), createdVoter.getName());

        verify(votersRepository, times(1)).save(any(Voter.class));
    }

    @Test
    public void testUpdateVoter() {
        Long voterId = 1L;
        Voter existingVoter = new Voter();
        existingVoter.setId(voterId);
        existingVoter.setName("John Doe");

        Voter updatedVoter = new Voter();
        updatedVoter.setId(voterId);
        updatedVoter.setName("Jane Smith");

        when(votersRepository.findById(voterId)).thenReturn(Optional.of(existingVoter));
        when(votersRepository.save(any(Voter.class))).thenReturn(updatedVoter);

        Optional<Voter> result = votersService.updateVoter(voterId, updatedVoter);

        Assertions.assertTrue(result.isPresent());
        Voter savedVoter = result.get();
        Assertions.assertEquals(updatedVoter.getId(), savedVoter.getId());
        Assertions.assertEquals(updatedVoter.getName(), savedVoter.getName());

        verify(votersRepository, times(1)).findById(voterId);
        verify(votersRepository, times(1)).save(any(Voter.class));
    }

    @Test
    public void testDeleteVoter() {
        Long voterId = 1L;

        doNothing().when(votersRepository).deleteById(voterId);

        votersService.deleteVoter(voterId);

        verify(votersRepository, times(1)).deleteById(voterId);
    }





    @Mock
    private CandidateRepository candidateRepository;




    @Test
    public void testVote() {
        // Create a mock voter
        Voter voter = new Voter();
        voter.setId(1L);

        // Create a mock candidate
        Candidate candidate = new Candidate();
        candidate.setPositionTitle("Position 1");
        candidate.setNbrVotes(0);

        // Configure the mock repositories
        when(votersRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(candidateRepository.findByPositionTitle("Position 1")).thenReturn(Optional.of(candidate));

        // Perform the vote
        votersService.vote(1L, "Position 1");

        // Verify that the vote is recorded
        verify(votersRepository, times(1)).save(voter);
        verify(candidateRepository, times(1)).save(candidate);

        // Assert that the vote count has increased
        assert candidate.getNbrVotes() == 1;
    }

    @Test
    public void testVoteSamePositionTwice() {
        Long voterId = 1L;
        String positionTitle = "President";

        // Create a Voter object
        Voter voter = new Voter();
        voter.setId(voterId);
        voter.setName("John Doe");

        // Configure the mock behavior for retrieving the voter
        when(votersRepository.findById(voterId)).thenReturn(Optional.of(voter));

        // Mark the voter as having already voted for the position
        voter.addVotedPosition(positionTitle);

        // Call the method under test and expect an exception
        assertThrows(IllegalArgumentException.class, () -> {
            votersService.vote(voterId, positionTitle);
        });

        // Verify that the findByPositionTitle method was not called on the candidate repository
        verify(candidateRepository, never()).findByPositionTitle(positionTitle);
    }

    @Test
    public void duplicateVote() {
        // Test data
        Long voterId = 1L;
        String positionTitle = "President";

        // Mocking the behavior of the repository
        Voter voter = new Voter();
        voter.setId(voterId);
        voter.setName("John Doe");
        voter.addVotedPosition(positionTitle); // Add the voted position

        when(votersRepository.findById(voterId)).thenReturn(Optional.of(voter));

        // Perform the vote
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            votersService.vote(voterId, positionTitle);
        });

        verify(votersRepository, times(1)).findById(voterId);
    }

}
