package com.Votechainbackend.BackendofADEIVotechain.UnitTest;

import com.Votechainbackend.BackendofADEIVotechain.dto.LoginRequest;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Import necessary dependencies

public class LoginRequestTest {

    @Test
    public void testValidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setApogeecodeOrEmail("testuser");
        loginRequest.setPassword("testpassword");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("apogeecodeOrEmail")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}
