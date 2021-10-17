package com.edorm.services.rooms;

import com.edorm.entities.rooms.Room;
import com.edorm.repositories.rooms.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoom(String number) {
        return roomRepository.findByNumber(number)
                .orElseThrow(NullPointerException::new);
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }


    public void deleteRoom(String number) {
        roomRepository.deleteByNumber(number);
    }

}
