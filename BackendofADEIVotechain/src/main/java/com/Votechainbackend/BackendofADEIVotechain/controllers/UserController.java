package com.Votechainbackend.BackendofADEIVotechain.controllers;

import com.Votechainbackend.BackendofADEIVotechain.dto.*;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.exceptions.ResourceNotFoundException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ElectionService electionService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("isAuthenticated()")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkApogeecodeAvailability")
    public UserIdentityAvailability checkApogeecodeAvailability(@RequestParam(value = "apogeecode") String apogeecode) {
        Boolean isAvailable = !userRepository.existsByApogeecode(apogeecode);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{apogeecode}")
    public UserProfile getUserProfile(@PathVariable(value = "apogeecode") String apogeecode) {
        User user = userRepository.findByApogeecode(apogeecode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "apogeecode", apogeecode));

        long electionCount = electionRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getApogeecode(), user.getName(), user.getCreatedAt(), electionCount, voteCount);

        return userProfile;
    }

    @GetMapping("/users/{apogeecode}/elections")
    public PagedResponse<ElectionResponse> getElectionsCreatedBy(@PathVariable(value = "apogeecode") String apogeecode,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return electionService.getElectionsCreatedBy(apogeecode, currentUser, page, size);
    }

    @GetMapping("/users/{apogeecode}/votes")
    public PagedResponse<ElectionResponse> getElectionsVotedBy(@PathVariable(value = "apogeecode") String apogeecode,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return electionService.getElectionsVotedBy(apogeecode, currentUser, page, size);
    }

}
