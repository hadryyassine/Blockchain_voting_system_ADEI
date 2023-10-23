package com.Votechainbackend.BackendofADEIVotechain.controllers;


import com.Votechainbackend.BackendofADEIVotechain.dto.*;
import com.Votechainbackend.BackendofADEIVotechain.entities.Election;
import com.Votechainbackend.BackendofADEIVotechain.repositories.ElectionRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.VoteRepository;
import com.Votechainbackend.BackendofADEIVotechain.security.CurrentUser;
import com.Votechainbackend.BackendofADEIVotechain.security.UserPrincipal;
import com.Votechainbackend.BackendofADEIVotechain.services.ElectionService;
import com.Votechainbackend.BackendofADEIVotechain.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElectionService electionService;

    private static final Logger logger = LoggerFactory.getLogger(ElectionController.class);

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<ElectionResponse> getElections(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return electionService.getAllElections(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createElection(@Valid @RequestBody ElectionRequest electionRequest) {

        Election election = electionService.createElection(electionRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{electionId}")
                .buildAndExpand(election.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Election Created Successfully"));
    }

    @GetMapping("/{electionId}")
    public ElectionResponse getElectionById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long electionId) {
        return electionService.getElectionById(electionId, currentUser);
    }

    @PostMapping("/{electionId}/votes")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ElectionResponse castVote(@CurrentUser UserPrincipal currentUser,
                         @PathVariable Long electionId,
                         @Valid @RequestBody VoteRequest voteRequest) {
        return electionService.castVoteAndGetUpdatedElection(electionId, voteRequest, currentUser);
    }

}
