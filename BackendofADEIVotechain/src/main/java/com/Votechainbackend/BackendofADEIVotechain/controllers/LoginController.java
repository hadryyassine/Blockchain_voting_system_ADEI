package com.Votechainbackend.BackendofADEIVotechain.controllers;

import com.Votechainbackend.BackendofADEIVotechain.dto.LoginRequest;
import com.Votechainbackend.BackendofADEIVotechain.dto.LoginResponse;
import com.Votechainbackend.BackendofADEIVotechain.security.CustomUserDetails;
import com.Votechainbackend.BackendofADEIVotechain.services.UserService;
import com.Votechainbackend.BackendofADEIVotechain.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword()));

        // Get the UserDetails from the Authentication object
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
