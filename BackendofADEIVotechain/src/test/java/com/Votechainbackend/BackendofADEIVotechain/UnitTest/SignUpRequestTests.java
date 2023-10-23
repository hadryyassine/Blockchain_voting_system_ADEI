package com.Votechainbackend.BackendofADEIVotechain.UnitTest;

import com.Votechainbackend.BackendofADEIVotechain.dto.SignUpRequest;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Import necessary dependencies

public class SignUpRequestTests {

    @Test
    public void testValidSignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("John Doe");
        signUpRequest.setApogeecode("12345");
        signUpRequest.setEmail("john@example.com");
        signUpRequest.setPassword("password");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidSignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);

        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("apogeecode")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}