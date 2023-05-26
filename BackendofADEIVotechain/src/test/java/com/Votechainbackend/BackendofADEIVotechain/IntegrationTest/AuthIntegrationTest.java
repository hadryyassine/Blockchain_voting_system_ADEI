package com.Votechainbackend.BackendofADEIVotechain.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserRegistrationAndAuthentication() throws Exception {
        // User registration
        String registrationRequest = "{\"name\":\"John Doe\",\"apogeecode\":\"9242023\",\"email\":\"john.doe00@example.com\",\"password\":\"password123\"}";
        MvcResult registrationResult = mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationRequest))
                .andExpect(status().isCreated())
                .andReturn();

        // User authentication
        String authenticationRequest = "{\"apogeecodeOrEmail\":\"john.doe00@example.com\",\"password\":\"password123\"}";
        MvcResult authenticationResult = mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authenticationRequest))
                .andExpect(status().isOk())
                .andReturn();

        // Verify JWT token received upon authentication
        String jwtToken = authenticationResult.getResponse().getContentAsString();
        assertThat(jwtToken).isNotBlank();

    }
}

