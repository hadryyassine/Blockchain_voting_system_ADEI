package com.example.VotingSystem.repositories;

import com.example.VotingSystem.entities.Election;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends CrudRepository<Election,Long> {
}
