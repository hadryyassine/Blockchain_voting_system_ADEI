package com.Votechainbackend.BackendofADEIVotechain.repositories;


import com.Votechainbackend.BackendofADEIVotechain.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
