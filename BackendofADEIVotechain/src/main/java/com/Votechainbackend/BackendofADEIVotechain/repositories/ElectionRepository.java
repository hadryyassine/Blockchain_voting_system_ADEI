package com.Votechainbackend.BackendofADEIVotechain.repositories;
import com.Votechainbackend.BackendofADEIVotechain.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<Election,Long> {
}
