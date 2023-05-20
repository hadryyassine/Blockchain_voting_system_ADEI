package com.Votechainbackend.BackendofADEIVotechain.repositories;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);

    Optional<User> findByUsername(String username);

    Optional<User> findByApogeeCode(String ApogeeCode);

    Boolean existsByUsername(String username);


    Boolean existsByApogeeCode(String ApogeeCode);

    Boolean existsByEmailAddress(String emailAddress);
}