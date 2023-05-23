package com.Votechainbackend.BackendofADEIVotechain.repositories;


import com.Votechainbackend.BackendofADEIVotechain.entities.Role;
import com.Votechainbackend.BackendofADEIVotechain.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
