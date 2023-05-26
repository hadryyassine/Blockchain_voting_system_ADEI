package com.Votechainbackend.BackendofADEIVotechain.UnitTest;

import com.Votechainbackend.BackendofADEIVotechain.dto.VoteRequest;
import com.Votechainbackend.BackendofADEIVotechain.entities.*;
import com.Votechainbackend.BackendofADEIVotechain.exceptions.BadRequestException;
import com.Votechainbackend.BackendofADEIVotechain.exceptions.ResourceNotFoundException;
import com.Votechainbackend.BackendofADEIVotechain.repositories.ElectionRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.VoteRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.services.ElectionService;
import com.Votechainbackend.BackendofADEIVotechain.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoteTest {

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ElectionService electionService;



    @Test
    public void testCastVoteAndGetUpdatedElection_ElectionNotFound() {
        // Mock the required objects
        UserPrincipal currentUser = new UserPrincipal(1L, "John Doe", "12345", "john@example.com", "password", Collections.emptyList());

        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setCandidateId(1L);

        when(electionRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Perform the voting (expecting an exception)
        try {
            electionService.castVoteAndGetUpdatedElection(1L, voteRequest, currentUser);
        } catch (ResourceNotFoundException e) {
            assertEquals("Election", e.getResourceName());
            assertEquals("id", e.getFieldName());
        }
    }

    @Test
    public void testCastVoteAndGetUpdatedElection_ElectionExpired() {
        // Mock the required objects
        Election election = new Election();
        election.setId(1L);
        election.setPositiontitle("Position Title");
        election.setExpirationDateTime(Instant.now().minus(Duration.ofDays(1)));

        UserPrincipal currentUser = new UserPrincipal(1L, "John Doe", "12345", "john@example.com", "password", Collections.emptyList());

        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setCandidateId(1L);

        when(electionRepository.findById(anyLong())).thenReturn(Optional.of(election));

        // Perform the voting (expecting an exception)
        try {
            electionService.castVoteAndGetUpdatedElection(election.getId(), voteRequest, currentUser);
        } catch (BadRequestException e) {
            assertEquals("Sorry! This Election has already expired", e.getMessage());
        }
    }
}

