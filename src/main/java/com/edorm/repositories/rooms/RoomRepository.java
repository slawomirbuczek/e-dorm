package com.edorm.repositories.rooms;

import com.edorm.entities.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByNumber(String number);

    void deleteByNumber(String number);

}
