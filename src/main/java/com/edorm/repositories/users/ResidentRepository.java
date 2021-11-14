package com.edorm.repositories.users;

import com.edorm.entities.users.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {


    Optional<Resident> findByUserId(long userId);

}
