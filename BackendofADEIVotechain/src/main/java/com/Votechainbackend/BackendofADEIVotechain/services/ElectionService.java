package com.Votechainbackend.BackendofADEIVotechain.services;


import com.Votechainbackend.BackendofADEIVotechain.dto.PagedResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.ElectionRequest;
import com.Votechainbackend.BackendofADEIVotechain.dto.ElectionResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.VoteRequest;
import com.Votechainbackend.BackendofADEIVotechain.entities.*;
import com.Votechainbackend.BackendofADEIVotechain.exceptions.BadRequestException;
import com.Votechainbackend.BackendofADEIVotechain.exceptions.ResourceNotFoundException;
import com.Votechainbackend.BackendofADEIVotechain.repositories.ElectionRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.UserRepository;
import com.Votechainbackend.BackendofADEIVotechain.repositories.VoteRepository;
import com.Votechainbackend.BackendofADEIVotechain.security.UserPrincipal;
import com.Votechainbackend.BackendofADEIVotechain.utils.AppConstants;
import com.Votechainbackend.BackendofADEIVotechain.utils.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ElectionService.class);

    public PagedResponse<ElectionResponse> getAllElections(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Election> elections = electionRepository.findAll(pageable);

        if(elections.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), elections.getNumber(),
                    elections.getSize(), elections.getTotalElements(), elections.getTotalPages(), elections.isLast());
        }

        List<Long> electionIds = elections.map(Election::getId).getContent();
        Map<Long, Long> candidateVoteCountMap = getCandidateVoteCountMap(electionIds);
        Map<Long, Long> electionUserVoteMap = getElectionUserVoteMap(currentUser, electionIds);
        Map<Long, User> creatorMap = getElectionCreatorMap(elections.getContent());

        List<ElectionResponse> electionResponses = elections.map(election -> {
            return ModelMapper.mapElectionToElectionResponse(election,
                    candidateVoteCountMap,
                    creatorMap.get(election.getCreatedBy()),
                    electionUserVoteMap == null ? null : electionUserVoteMap.getOrDefault(election.getId(), null));
        }).getContent();

        return new PagedResponse<>(electionResponses, elections.getNumber(),
                elections.getSize(), elections.getTotalElements(), elections.getTotalPages(), elections.isLast());
    }

    public PagedResponse<ElectionResponse> getElectionsCreatedBy(String apogeecode, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByApogeecode(apogeecode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "apogeecode", apogeecode));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Election> elections = electionRepository.findByCreatedBy(user.getId(), pageable);

        if (elections.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), elections.getNumber(),
                    elections.getSize(), elections.getTotalElements(), elections.getTotalPages(), elections.isLast());
        }

        List<Long> electionIds = elections.map(Election::getId).getContent();
        Map<Long, Long> candidateVoteCountMap = getCandidateVoteCountMap(electionIds);
        Map<Long, Long> electionUserVoteMap = getElectionUserVoteMap(currentUser, electionIds);

        List<ElectionResponse> electionResponses = elections.map(election -> {
            return ModelMapper.mapElectionToElectionResponse(election,
                    candidateVoteCountMap,
                    user,
                    electionUserVoteMap == null ? null : electionUserVoteMap.getOrDefault(election.getId(), null));
        }).getContent();

        return new PagedResponse<>(electionResponses, elections.getNumber(),
                elections.getSize(), elections.getTotalElements(), elections.getTotalPages(), elections.isLast());
    }

    public PagedResponse<ElectionResponse> getElectionsVotedBy(String apogeecode, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByApogeecode(apogeecode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "apogeecode", apogeecode));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Long> userVotedElectionIds = voteRepository.findVotedElectionIdsByUserId(user.getId(), pageable);

        if (userVotedElectionIds.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), userVotedElectionIds.getNumber(),
                    userVotedElectionIds.getSize(), userVotedElectionIds.getTotalElements(),
                    userVotedElectionIds.getTotalPages(), userVotedElectionIds.isLast());
        }

        List<Long> electionIds = userVotedElectionIds.getContent();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Election> elections = electionRepository.findByIdIn(electionIds, sort);

        Map<Long, Long> candidateVoteCountMap = getCandidateVoteCountMap(electionIds);
        Map<Long, Long> electionUserVoteMap = getElectionUserVoteMap(currentUser, electionIds);
        Map<Long, User> creatorMap = getElectionCreatorMap(elections);

        List<ElectionResponse> electionResponses = elections.stream().map(election -> {
            return ModelMapper.mapElectionToElectionResponse(election,
                    candidateVoteCountMap,
                    creatorMap.get(election.getCreatedBy()),
                    electionUserVoteMap == null ? null : electionUserVoteMap.getOrDefault(election.getId(), null));
        }).collect(Collectors.toList());

        return new PagedResponse<>(electionResponses, userVotedElectionIds.getNumber(), userVotedElectionIds.getSize(), userVotedElectionIds.getTotalElements(), userVotedElectionIds.getTotalPages(), userVotedElectionIds.isLast());
    }


    public Election createElection(ElectionRequest electionRequest) {
        Election election = new Election();
        election.setPositiontitle(electionRequest.getPositiontitle());

        electionRequest.getCandidates().forEach(candidateRequest -> {
            election.addCandidate(new Candidate(candidateRequest.getName()));
        });

        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(electionRequest.getElectionLength().getDays()))
                .plus(Duration.ofHours(electionRequest.getElectionLength().getHours()));

        election.setExpirationDateTime(expirationDateTime);

        return electionRepository.save(election);
    }

    public ElectionResponse getElectionById(Long electionId, UserPrincipal currentUser) {
        Election election = electionRepository.findById(electionId).orElseThrow(
                () -> new ResourceNotFoundException("Election", "id", electionId));

        List<CandidateVoteCount> votes = voteRepository.countByElectionIdGroupByCandidateId(electionId);

        Map<Long, Long> candidateVotesMap = votes.stream()
                .collect(Collectors.toMap(CandidateVoteCount::getCandidateId, CandidateVoteCount::getVoteCount));

        User creator = userRepository.findById(election.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", election.getCreatedBy()));

        Vote userVote = null;
        if(currentUser != null) {
            userVote = voteRepository.findByUserIdAndElectionId(currentUser.getId(), electionId);
        }

        return ModelMapper.mapElectionToElectionResponse(election, candidateVotesMap,
                creator, userVote != null ? userVote.getCandidate().getId(): null);
    }

    public ElectionResponse castVoteAndGetUpdatedElection(Long electionId, VoteRequest voteRequest, UserPrincipal currentUser) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new ResourceNotFoundException("Election", "id", electionId));

        if(election.getExpirationDateTime().isBefore(Instant.now())) {
            throw new BadRequestException("Sorry! This Election has already expired");
        }

        User user = userRepository.getOne(currentUser.getId());

        Candidate selectedCandidate = election.getCandidates().stream()
                .filter(candidate -> candidate.getId().equals(voteRequest.getCandidateId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", voteRequest.getCandidateId()));

        Vote vote = new Vote();
        vote.setElection(election);
        vote.setUser(user);
        vote.setCandidate(selectedCandidate);

        try {
            vote = voteRepository.save(vote);
        } catch (DataIntegrityViolationException ex) {
            logger.info("User {} has already voted in Election {}", currentUser.getId(), electionId);
            throw new BadRequestException("Sorry! You have already cast your vote in this election");
        }


        List<CandidateVoteCount> votes = voteRepository.countByElectionIdGroupByCandidateId(electionId);

        Map<Long, Long> candidateVotesMap = votes.stream()
                .collect(Collectors.toMap(CandidateVoteCount::getCandidateId, CandidateVoteCount::getVoteCount));

        User creator = userRepository.findById(election.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", election.getCreatedBy()));

        return ModelMapper.mapElectionToElectionResponse(election, candidateVotesMap, creator, vote.getCandidate().getId());
    }


    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    private Map<Long, Long> getCandidateVoteCountMap(List<Long> electionIds) {
        // Retrieve Vote Counts of every Candidate belonging to the given electionIds
        List<CandidateVoteCount> votes = voteRepository.countByElectionIdInGroupByCandidateId(electionIds);

        Map<Long, Long> candidateVotesMap = votes.stream()
                .collect(Collectors.toMap(CandidateVoteCount::getCandidateId, CandidateVoteCount::getVoteCount));

        return candidateVotesMap;
    }

    private Map<Long, Long> getElectionUserVoteMap(UserPrincipal currentUser, List<Long> electionIds) {
        Map<Long, Long> electionUserVoteMap = null;
        if(currentUser != null) {
            List<Vote> userVotes = voteRepository.findByUserIdAndElectionIdIn(currentUser.getId(), electionIds);

            electionUserVoteMap = userVotes.stream()
                    .collect(Collectors.toMap(vote -> vote.getElection().getId(), vote -> vote.getCandidate().getId()));
        }
        return electionUserVoteMap;
    }

    Map<Long, User> getElectionCreatorMap(List<Election> elections) {
        // Get Election Creator details of the given list of elections
        List<Long> creatorIds = elections.stream()
                .map(Election::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}
