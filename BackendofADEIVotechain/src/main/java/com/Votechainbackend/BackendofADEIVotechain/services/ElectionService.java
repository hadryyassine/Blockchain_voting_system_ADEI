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
    private ElectionRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ElectionService.class);

    public PagedResponse<ElectionResponse> getAllElections(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Elections
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Election> polls = pollRepository.findAll(pageable);

        if(polls.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        // Map Elections to ElectionResponses containing vote counts and poll creator details
        List<Long> pollIds = polls.map(Election::getId).getContent();
        Map<Long, Long> candidateVoteCountMap = getCandidateVoteCountMap(pollIds);
        Map<Long, Long> pollUserVoteMap = getElectionUserVoteMap(currentUser, pollIds);
        Map<Long, User> creatorMap = getElectionCreatorMap(polls.getContent());

        List<ElectionResponse> pollResponses = polls.map(poll -> {
            return ModelMapper.mapElectionToElectionResponse(poll,
                    candidateVoteCountMap,
                    creatorMap.get(poll.getCreatedBy()),
                    pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null));
        }).getContent();

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    public PagedResponse<ElectionResponse> getElectionsCreatedBy(String apogeecode, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByApogeecode(apogeecode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "apogeecode", apogeecode));

        // Retrieve all polls created by the given apogeecode
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Election> polls = pollRepository.findByCreatedBy(user.getId(), pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        // Map Elections to ElectionResponses containing vote counts and poll creator details
        List<Long> pollIds = polls.map(Election::getId).getContent();
        Map<Long, Long> candidateVoteCountMap = getCandidateVoteCountMap(pollIds);
        Map<Long, Long> pollUserVoteMap = getElectionUserVoteMap(currentUser, pollIds);

        List<ElectionResponse> pollResponses = polls.map(poll -> {
            return ModelMapper.mapElectionToElectionResponse(poll,
                    candidateVoteCountMap,
                    user,
                    pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null));
        }).getContent();

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    public PagedResponse<ElectionResponse> getElectionsVotedBy(String apogeecode, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByApogeecode(apogeecode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "apogeecode", apogeecode));

        // Retrieve all pollIds in which the given apogeecode has voted
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Long> userVotedElectionIds = voteRepository.findVotedElectionIdsByUserId(user.getId(), pageable);

        if (userVotedElectionIds.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), userVotedElectionIds.getNumber(),
                    userVotedElectionIds.getSize(), userVotedElectionIds.getTotalElements(),
                    userVotedElectionIds.getTotalPages(), userVotedElectionIds.isLast());
        }

        // Retrieve all poll details from the voted pollIds.
        List<Long> pollIds = userVotedElectionIds.getContent();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Election> polls = pollRepository.findByIdIn(pollIds, sort);

        // Map Elections to ElectionResponses containing vote counts and poll creator details
        Map<Long, Long> candidateVoteCountMap = getCandidateVoteCountMap(pollIds);
        Map<Long, Long> pollUserVoteMap = getElectionUserVoteMap(currentUser, pollIds);
        Map<Long, User> creatorMap = getElectionCreatorMap(polls);

        List<ElectionResponse> pollResponses = polls.stream().map(poll -> {
            return ModelMapper.mapElectionToElectionResponse(poll,
                    candidateVoteCountMap,
                    creatorMap.get(poll.getCreatedBy()),
                    pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null));
        }).collect(Collectors.toList());

        return new PagedResponse<>(pollResponses, userVotedElectionIds.getNumber(), userVotedElectionIds.getSize(), userVotedElectionIds.getTotalElements(), userVotedElectionIds.getTotalPages(), userVotedElectionIds.isLast());
    }


    public Election createElection(ElectionRequest pollRequest) {
        Election poll = new Election();
        poll.setPositiontitle(pollRequest.getPositiontitle());

        pollRequest.getCandidates().forEach(candidateRequest -> {
            poll.addCandidate(new Candidate(candidateRequest.getName()));
        });

        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(pollRequest.getElectionLength().getDays()))
                .plus(Duration.ofHours(pollRequest.getElectionLength().getHours()));

        poll.setExpirationDateTime(expirationDateTime);

        return pollRepository.save(poll);
    }

    public ElectionResponse getElectionById(Long pollId, UserPrincipal currentUser) {
        Election poll = pollRepository.findById(pollId).orElseThrow(
                () -> new ResourceNotFoundException("Election", "id", pollId));

        // Retrieve Vote Counts of every candidate belonging to the current poll
        List<CandidateVoteCount> votes = voteRepository.countByElectionIdGroupByCandidateId(pollId);

        Map<Long, Long> candidateVotesMap = votes.stream()
                .collect(Collectors.toMap(CandidateVoteCount::getCandidateId, CandidateVoteCount::getVoteCount));

        // Retrieve poll creator details
        User creator = userRepository.findById(poll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));

        // Retrieve vote done by logged in user
        Vote userVote = null;
        if(currentUser != null) {
            userVote = voteRepository.findByUserIdAndElectionId(currentUser.getId(), pollId);
        }

        return ModelMapper.mapElectionToElectionResponse(poll, candidateVotesMap,
                creator, userVote != null ? userVote.getCandidate().getId(): null);
    }

    public ElectionResponse castVoteAndGetUpdatedElection(Long pollId, VoteRequest voteRequest, UserPrincipal currentUser) {
        Election poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Election", "id", pollId));

        if(poll.getExpirationDateTime().isBefore(Instant.now())) {
            throw new BadRequestException("Sorry! This Election has already expired");
        }

        User user = userRepository.getOne(currentUser.getId());

        Candidate selectedCandidate = poll.getCandidates().stream()
                .filter(candidate -> candidate.getId().equals(voteRequest.getCandidateId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", voteRequest.getCandidateId()));

        Vote vote = new Vote();
        vote.setElection(poll);
        vote.setUser(user);
        vote.setCandidate(selectedCandidate);

        try {
            vote = voteRepository.save(vote);
        } catch (DataIntegrityViolationException ex) {
            logger.info("User {} has already voted in Election {}", currentUser.getId(), pollId);
            throw new BadRequestException("Sorry! You have already cast your vote in this poll");
        }

        //-- Vote Saved, Return the updated Election Response now --

        // Retrieve Vote Counts of every candidate belonging to the current poll
        List<CandidateVoteCount> votes = voteRepository.countByElectionIdGroupByCandidateId(pollId);

        Map<Long, Long> candidateVotesMap = votes.stream()
                .collect(Collectors.toMap(CandidateVoteCount::getCandidateId, CandidateVoteCount::getVoteCount));

        // Retrieve poll creator details
        User creator = userRepository.findById(poll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));

        return ModelMapper.mapElectionToElectionResponse(poll, candidateVotesMap, creator, vote.getCandidate().getId());
    }


    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    private Map<Long, Long> getCandidateVoteCountMap(List<Long> pollIds) {
        // Retrieve Vote Counts of every Candidate belonging to the given pollIds
        List<CandidateVoteCount> votes = voteRepository.countByElectionIdInGroupByCandidateId(pollIds);

        Map<Long, Long> candidateVotesMap = votes.stream()
                .collect(Collectors.toMap(CandidateVoteCount::getCandidateId, CandidateVoteCount::getVoteCount));

        return candidateVotesMap;
    }

    private Map<Long, Long> getElectionUserVoteMap(UserPrincipal currentUser, List<Long> pollIds) {
        Map<Long, Long> pollUserVoteMap = null;
        if(currentUser != null) {
            List<Vote> userVotes = voteRepository.findByUserIdAndElectionIdIn(currentUser.getId(), pollIds);

            pollUserVoteMap = userVotes.stream()
                    .collect(Collectors.toMap(vote -> vote.getElection().getId(), vote -> vote.getCandidate().getId()));
        }
        return pollUserVoteMap;
    }

    Map<Long, User> getElectionCreatorMap(List<Election> polls) {
        // Get Election Creator details of the given list of polls
        List<Long> creatorIds = polls.stream()
                .map(Election::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}
