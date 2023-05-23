package com.Votechainbackend.BackendofADEIVotechain.repositories;

import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByApogeecodeOrEmail(String apogeecode, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByApogeecode(String apogeecode);

    Boolean existsByApogeecode(String apogeecode);

    Boolean existsByEmail(String email);
}
