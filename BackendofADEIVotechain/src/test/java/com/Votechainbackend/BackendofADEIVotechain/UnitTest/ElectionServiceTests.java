package com.Votechainbackend.BackendofADEIVotechain.UnitTest;

import com.Votechainbackend.BackendofADEIVotechain.dto.*;
import com.Votechainbackend.BackendofADEIVotechain.entities.*;
import com.Votechainbackend.BackendofADEIVotechain.repositories.ElectionRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.VoteRepository;
import com.Votechainbackend.BackendofADEIVotechain.security.UserPrincipal;
import com.Votechainbackend.BackendofADEIVotechain.services.ElectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ElectionServiceTests {

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ElectionService electionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testGetAllElections() {
//        // Prepare mock data
//        int page = 0;
//        int size = 10;
//
//        List<Election> electionList = Collections.singletonList(createMockElection());
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Election> electionPage = new PageImpl<>(electionList, pageable, electionList.size());
//
//        when(electionRepository.findAll(pageable)).thenReturn(electionPage);
//
//        // Call the service method
//        PagedResponse<ElectionResponse> response = electionService.getAllElections(createMockUserPrincipal(), page, size);
//
//        // Verify the results
//        assertNotNull(response);
//        assertEquals(1, response.getContent().size());
//        assertEquals(electionList.get(0).getId(), response.getContent().get(0).getId());
//        // ... add more assertions as needed
//    }

    @Test
    public void testCreateElection() {
        // Prepare mock data
        ElectionRequest electionRequest = createMockElectionRequest();

        Election election = createMockElection();

        when(electionRepository.save(any(Election.class))).thenReturn(election);

        // Call the service method
        Election result = electionService.createElection(electionRequest);

        // Verify the results
        assertNotNull(result);
        assertEquals(election.getId(), result.getId());
        // ... add more assertions as needed
    }



    private UserPrincipal createMockUserPrincipal() {
        // Create a mock User object
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setApogeecode("123456");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        // Create a mock Role object
        Role role = new Role(RoleName.ROLE_ADMIN);

        // Set the roles for the user
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Create a mock GrantedAuthority object
        GrantedAuthority authority = new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name());

        // Create a collection of authorities
        Collection<GrantedAuthority> authorities = Collections.singletonList(authority);

        // Create and return the UserPrincipal object
        return new UserPrincipal(user.getId(), user.getName(), user.getApogeecode(),
                user.getEmail(), user.getPassword(), authorities);
    }

    private ElectionRequest createMockElectionRequest() {
        ElectionRequest electionRequest = new ElectionRequest();
        electionRequest.setPositiontitle("Position Title");

        List<CandidateRequest> candidateRequests = new ArrayList<>();
        candidateRequests.add(new CandidateRequest("Candidate 1"));
        candidateRequests.add(new CandidateRequest("Candidate 2"));

        electionRequest.setCandidates(candidateRequests);

        ElectionLength electionLength = new ElectionLength();
        electionLength.setDays(7);
        electionLength.setHours(24);
        electionRequest.setElectionLength(electionLength);

        return electionRequest;
    }
    private Candidate createMockCandidate(String name, Election election) {
        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setElection(election);
        return candidate;
    }


    private Election createMockElection() {
        Election election = new Election();
        election.setId(1L);
        election.setPositiontitle("Position Title");

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(createMockCandidate("Candidate 1", election));
        candidates.add(createMockCandidate("Candidate 2", election));

        election.setCandidates(candidates);

        Instant expirationDateTime = Instant.now().plus(Duration.ofDays(7)).plus(Duration.ofHours(24));
        election.setExpirationDateTime(expirationDateTime);

        return election;
    }




}
