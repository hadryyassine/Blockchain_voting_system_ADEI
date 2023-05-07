package com.Votechainbackend.BackendofADEIVotechain.repositories;
import com.Votechainbackend.BackendofADEIVotechain.entities.Committee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CommitteeRepository  extends JpaRepository<Committee,Long> {
}
