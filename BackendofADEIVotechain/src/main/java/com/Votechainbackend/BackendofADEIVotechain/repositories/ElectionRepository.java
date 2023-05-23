package com.Votechainbackend.BackendofADEIVotechain.repositories;

import com.Votechainbackend.BackendofADEIVotechain.entities.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    Optional<Election> findById(Long electionId);

    Page<Election> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Election> findByIdIn(List<Long> electionIds);

    List<Election> findByIdIn(List<Long> electionIds, Sort sort);
}
