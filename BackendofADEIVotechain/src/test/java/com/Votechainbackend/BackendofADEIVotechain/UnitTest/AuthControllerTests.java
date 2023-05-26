package com.Votechainbackend.BackendofADEIVotechain.UnitTest;

import com.Votechainbackend.BackendofADEIVotechain.controllers.AuthController;
import com.Votechainbackend.BackendofADEIVotechain.dto.ApiResponse;
import com.Votechainbackend.BackendofADEIVotechain.entities.Role;
import com.Votechainbackend.BackendofADEIVotechain.entities.RoleName;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import com.Votechainbackend.BackendofADEIVotechain.dto.LoginRequest;
import com.Votechainbackend.BackendofADEIVotechain.dto.SignUpRequest;
import com.Votechainbackend.BackendofADEIVotechain.repositories.RoleRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTests {

    private final String AUTH_ENDPOINT = "/api/auth";

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    public AuthControllerTests() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setApogeecodeOrEmail("john");
        loginRequest.setPassword("password");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = "test.jwt.token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenProvider.generateToken(any(Authentication.class))).thenReturn(jwt);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_ENDPOINT + "/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value(jwt));
    }

    @Test
    public void testRegisterUser() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("John Doe");
        signUpRequest.setApogeecode("john");
        signUpRequest.setEmail("john@example.com");
        signUpRequest.setPassword("password");

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setApogeecode(signUpRequest.getApogeecode());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());

        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);

        when(userRepository.existsByApogeecode(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(any(RoleName.class))).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_ENDPOINT + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    public void testRegisterUser_ApogeecodeAlreadyTaken() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("John Doe");
        signUpRequest.setApogeecode("john");
        signUpRequest.setEmail("john@example.com");
        signUpRequest.setPassword("password");

        when(userRepository.existsByApogeecode(anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_ENDPOINT + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Apogeecode is already taken!"));
    }

    @Test
    public void testRegisterUser_EmailAlreadyInUse() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("John Doe");
        signUpRequest.setApogeecode("john");
        signUpRequest.setEmail("john@example.com");
        signUpRequest.setPassword("password");

        when(userRepository.existsByApogeecode(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_ENDPOINT + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email Address already in use!"));
    }

    @Test
    public void testInvalidLoginCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setApogeecodeOrEmail("testuser");
        loginRequest.setPassword("invalidpassword");

        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> authController.authenticateUser(loginRequest));
    }

    @Test
    public void testRegistrationException() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Test User");
        signUpRequest.setApogeecode("testuser");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("testpassword");

        when(userRepository.existsByApogeecode(any())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(signUpRequest);
        ApiResponse responseBody = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Apogeecode is already taken!", responseBody.getMessage());
    }




}

