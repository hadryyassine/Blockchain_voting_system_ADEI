package com.Votechainbackend.BackendofADEIVotechain.repositories;

import com.Votechainbackend.BackendofADEIVotechain.entities.CandidateVoteCount;
import com.Votechainbackend.BackendofADEIVotechain.entities.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT NEW com.Votechainbackend.BackendofADEIVotechain.entities.CandidateVoteCount(v.candidate.id, count(v.id)) FROM Vote v WHERE v.election.id in :electionIds GROUP BY v.candidate.id")
    List<CandidateVoteCount> countByElectionIdInGroupByCandidateId(@Param("electionIds") List<Long> electionIds);

    @Query("SELECT NEW com.Votechainbackend.BackendofADEIVotechain.entities.CandidateVoteCount(v.candidate.id, count(v.id)) FROM Vote v WHERE v.election.id = :electionId GROUP BY v.candidate.id")
    List<CandidateVoteCount> countByElectionIdGroupByCandidateId(@Param("electionId") Long electionId);

    @Query("SELECT v FROM Vote v where v.user.id = :userId and v.election.id in :electionIds")
    List<Vote> findByUserIdAndElectionIdIn(@Param("userId") Long userId, @Param("electionIds") List<Long> electionIds);

    @Query("SELECT v FROM Vote v where v.user.id = :userId and v.election.id = :electionId")
    Vote findByUserIdAndElectionId(@Param("userId") Long userId, @Param("electionId") Long electionId);

    @Query("SELECT COUNT(v.id) from Vote v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.election.id FROM Vote v WHERE v.user.id = :userId")
    Page<Long> findVotedElectionIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}

