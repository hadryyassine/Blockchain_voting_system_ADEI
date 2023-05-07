package com.Votechainbackend.BackendofADEIVotechain.repositories;
import com.Votechainbackend.BackendofADEIVotechain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAddress(String emailAddress);
}