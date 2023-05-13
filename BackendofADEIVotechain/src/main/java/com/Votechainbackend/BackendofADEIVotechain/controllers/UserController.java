package com.Votechainbackend.BackendofADEIVotechain.controllers;

import com.Votechainbackend.BackendofADEIVotechain.services.CandidateService;
import com.Votechainbackend.BackendofADEIVotechain.services.CommitteeService;
import com.Votechainbackend.BackendofADEIVotechain.services.UserService;
import com.Votechainbackend.BackendofADEIVotechain.services.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final VoterService voterService;
    private final CandidateService candidateService;
    private final CommitteeService committeeService;

    @Autowired
    public UserController(UserService userService, VoterService voterService, CandidateService candidateService, CommitteeService committeeService) {
        this.userService = userService;
        this.voterService = voterService;
        this.candidateService = candidateService;
        this.committeeService = committeeService;
    }

    /***

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        switch (user.getRole()) {
            case VOTER:
                //VoterDashboard dashboardVoter = voterService.getDashboard(user);
               // return ResponseEntity.ok(dashboardVoter);

            case CANDIDATE:
                //CandidateDashboard dashboardCandidate = candidateService.getDashboard(user);
                //return ResponseEntity.ok(dashboardCandidate);

            case COMMITTEE:
                //CommitteeDashboard dashboardCommittee = committeeService.getDashboard(user);
                //return ResponseEntity.ok(dashboardCommittee);

            default:
                return ResponseEntity.badRequest().body("Invalid role");
        }
    } **/
}
