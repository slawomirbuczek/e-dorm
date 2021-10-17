package com.edorm.repositories.rooms;

import com.edorm.entities.rooms.Composition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, Long> {

    Optional<Composition> findByName(String name);

    void deleteByName(String name);

}
