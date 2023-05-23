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

    public static ElectionResponse mapElectionToElectionResponse(Election candidate, Map<Long, Long> candidateVotesMap, User creator, Long userVote) {
        ElectionResponse candidateResponse = new ElectionResponse();
        candidateResponse.setId(candidate.getId());
        candidateResponse.setPositiontitle(candidate.getPositiontitle());
        candidateResponse.setCreationDateTime(candidate.getCreatedAt());
        candidateResponse.setExpirationDateTime(candidate.getExpirationDateTime());
        Instant now = Instant.now();
        candidateResponse.setExpired(candidate.getExpirationDateTime().isBefore(now));

        List<CandidateResponse> candidateResponses = candidate.getCandidates().stream().map(candidate -> {
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

        candidateResponse.setCandidates(candidateResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getApogeecode(), creator.getName());
        candidateResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            candidateResponse.setSelectedCandidate(userVote);
        }

        long totalVotes = candidateResponse.getCandidates().stream().mapToLong(CandidateResponse::getVoteCount).sum();
        candidateResponse.setTotalVotes(totalVotes);

        return candidateResponse;
    }

}
