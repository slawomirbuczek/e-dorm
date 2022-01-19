package com.edorm.services.rooms;

import com.edorm.entities.rooms.Composition;
import com.edorm.entities.rooms.Room;
import com.edorm.models.rooms.AddRoomRequest;
import com.edorm.models.rooms.GetRoomResponse;
import com.edorm.repositories.rooms.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final CompositionService compositionService;

    public GetRoomResponse getRoomInfo(String roomNumber) {
        return roomRepository.findByNumber(roomNumber).map(this::mapRoomToResponse).orElseThrow(NullPointerException::new);
    }

    public List<GetRoomResponse> getRooms() {
        return roomRepository.findAll().stream().map(this::mapRoomToResponse).collect(Collectors.toList());
    }

    public void addRoom(AddRoomRequest request) {
        final Composition composition = compositionService.getComposition(request.getCompositionNumber());

        if (roomRepository.existsByNumber(request.getRoomNumber())) {
            throw new RuntimeException();
        }

        Room room = new Room();
        room.setNumber(request.getRoomNumber());
        room.setFloor(request.getFloor());
        room.setSize(request.getSize());
        room.setComposition(composition);
        room.setResidents(0);
        roomRepository.save(room);
    }

    public Room getRoom(String number) {
        return roomRepository.findByNumber(number).orElseThrow(NullPointerException::new);
    }

    private GetRoomResponse mapRoomToResponse(Room room) {
        GetRoomResponse response = new GetRoomResponse();
        response.setRoomNumber(room.getNumber());
        response.setFloor(room.getFloor());
        response.setResidents(room.getResidents());
        response.setSize(room.getSize());
        response.setCompositionNumber(room.getComposition().getNumber());
        return response;
    }

}
