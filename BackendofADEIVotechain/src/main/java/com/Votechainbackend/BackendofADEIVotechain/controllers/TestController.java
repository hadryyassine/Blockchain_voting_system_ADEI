package com.Votechainbackend.BackendofADEIVotechain.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('VOTER') or hasRole('CANDIDATE') or hasRole('COMMITTEE')")
    public String userAccess() {
        return "Voter Content.";
    }

    @GetMapping("/voter")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String moderatorAccess() {
        return "candidate Board.";
    }

    @GetMapping("/committee")
    @PreAuthorize("hasRole('COMMITTEE')")
    public String adminAccess() {
        return "Committee Board.";
    }
}