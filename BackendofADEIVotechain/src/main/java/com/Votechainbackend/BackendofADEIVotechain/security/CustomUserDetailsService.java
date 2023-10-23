package com.Votechainbackend.BackendofADEIVotechain.security;


import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.exceptions.ResourceNotFoundException;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String apogeecodeOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either apogeecode or email
        User user = userRepository.findByApogeecodeOrEmail(apogeecodeOrEmail, apogeecodeOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with apogeecode or email : " + apogeecodeOrEmail)
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}