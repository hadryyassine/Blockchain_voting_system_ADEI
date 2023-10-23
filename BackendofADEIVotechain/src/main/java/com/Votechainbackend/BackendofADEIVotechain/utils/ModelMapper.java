package com.Votechainbackend.BackendofADEIVotechain.utils;


import com.Votechainbackend.BackendofADEIVotechain.dto.CandidateResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.ElectionResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.UserSummary;
import com.Votechainbackend.BackendofADEIVotechain.entities.Election;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static ElectionResponse mapElectionToElectionResponse(Election election, Map<Long, Long> candidateVotesMap, User creator, Long userVote) {
        ElectionResponse electionResponse = new ElectionResponse();
        electionResponse.setId(election.getId());
        electionResponse.setPositiontitle(election.getPositiontitle());
        electionResponse.setCreationDateTime(election.getCreatedAt());
        electionResponse.setExpirationDateTime(election.getExpirationDateTime());
        Instant now = Instant.now();
        electionResponse.setExpired(election.getExpirationDateTime().isBefore(now));

        List<CandidateResponse> candidateResponses = election.getCandidates().stream().map(candidate -> {
            CandidateResponse candidateResponse = new CandidateResponse();
            candidateResponse.setId(candidate.getId());
            candidateResponse.setName(candidate.getName());

            if(candidateVotesMap.containsKey(candidate.getId())) {
                candidateResponse.setVoteCount(candidateVotesMap.get(candidate.getId()));
            } else {
                candidateResponse.setVoteCount(0);
            }
            return candidateResponse;
        }).collect(Collectors.toList());

        electionResponse.setCandidates(candidateResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getApogeecode(), creator.getName());
        electionResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            electionResponse.setSelectedCandidate(userVote);
        }

        long totalVotes = electionResponse.getCandidates().stream().mapToLong(CandidateResponse::getVoteCount).sum();
        electionResponse.setTotalVotes(totalVotes);

        return electionResponse;
    }

}
