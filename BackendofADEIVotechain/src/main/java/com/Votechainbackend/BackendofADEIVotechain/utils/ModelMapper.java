package com.Votechainbackend.BackendofADEIVotechain.utils;


import com.Votechainbackend.BackendofADEIVotechain.dto.CandidateResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.ElectionResponse;
import com.Votechainbackend.BackendofADEIVotechain.dto.UserSummary;
import com.Votechainbackend.BackendofADEIVotechain.entities.Poll;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static ElectionResponse mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator, Long userVote) {
        ElectionResponse pollResponse = new ElectionResponse();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setCreationDateTime(poll.getCreatedAt());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponse.setExpired(poll.getExpirationDateTime().isBefore(now));

        List<CandidateResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            CandidateResponse choiceResponse = new CandidateResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            if(choiceVotesMap.containsKey(choice.getId())) {
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList());

        pollResponse.setChoices(choiceResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        pollResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(CandidateResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }

}
