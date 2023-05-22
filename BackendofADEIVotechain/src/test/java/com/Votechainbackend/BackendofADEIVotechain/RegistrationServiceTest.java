/*
package com.Votechainbackend.BackendofADEIVotechain;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.entities.Role;

import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;
    private RegistrationService registrationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        registrationService = new RegistrationService(userRepository);
    }

    @Test
    public void register_userNotExists_returnsUser() {
        User user = new Voter(1L, "Test Name", "test@test.com", "12345678", "password", Role.VOTER, 1L);
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        User registeredUser = registrationService.register(user);

        assertEquals(user, registeredUser);
    }

    @Test
    public void register_userExists_throwsException() {
        User user = new Voter(1L, "Test Name", "test@test.com", "12345678", "password", Role.VOTER, 1L);
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class, () -> registrationService.register(user));
    }
}
*/
