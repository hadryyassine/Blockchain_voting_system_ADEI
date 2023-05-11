package com.Votechainbackend.BackendofADEIVotechain;

import com.Votechainbackend.BackendofADEIVotechain.entities.Role;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.entities.Voter;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

public class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user = new Voter(1L, "Test Name", "test@test.com", "12345678", "password", Role.VOTER, 1L);
    }

    @Test
    public void verifyLogin_correctPassword_returnsUser() {
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.of(user));

        User loggedInUser = loginService.verifyLogin(user.getEmailAddress(), user.getPassword());

        assertEquals(user, loggedInUser);
    }

    @Test
    public void verifyLogin_incorrectPassword_throwsException() {
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class, () -> {
            loginService.verifyLogin(user.getEmailAddress(), "incorrectPassword");
        });
    }

    @Test
    public void verifyLogin_userNotFound_throwsException() {
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            loginService.verifyLogin(user.getEmailAddress(), user.getPassword());
        });
    }
}

