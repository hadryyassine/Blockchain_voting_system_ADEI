package com.Votechainbackend.BackendofADEIVotechain.UnitTest;

import com.Votechainbackend.BackendofADEIVotechain.dto.VoteRequest;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Import necessary dependencies

public class VoteRequestTest {

    @Test
    public void testValidVoteRequest() {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setCandidateId(123L);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<VoteRequest>> violations = validator.validate(voteRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidVoteRequest() {
        VoteRequest voteRequest = new VoteRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<VoteRequest>> violations = validator.validate(voteRequest);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("candidateId")));
    }
}
