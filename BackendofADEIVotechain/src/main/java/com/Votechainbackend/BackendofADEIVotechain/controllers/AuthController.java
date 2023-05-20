package com.Votechainbackend.BackendofADEIVotechain.controllers;

import com.Votechainbackend.BackendofADEIVotechain.dto.JwtResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.LoginRequest;
import com.Votechainbackend.BackendofADEIVotechain.dto.MessageResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.SignupRequest;
import com.Votechainbackend.BackendofADEIVotechain.entities.Role;
import com.Votechainbackend.BackendofADEIVotechain.entities.RoleE;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.repositories.RoleRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.security.JwtUtils;
import com.Votechainbackend.BackendofADEIVotechain.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailAdress(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getApogeeCode(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmailAddress(signUpRequest.getEmailAdress())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        if (userRepository.existsByApogeeCode(signUpRequest.getApogeeCode())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Apogee Code is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getName(),signUpRequest.getEmailAdress(),
                signUpRequest.getApogeeCode(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleE> roles = new HashSet<>();

        if (strRoles == null) {
            RoleE userRole = roleRepository.findByName(Role.VOTER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {

                    case "Candidate":
                        RoleE adminRole = roleRepository.findByName(Role.CANDIDATE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "Committee":
                        RoleE modRole = roleRepository.findByName(Role.COMMITTEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        RoleE userRole = roleRepository.findByName(Role.VOTER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
