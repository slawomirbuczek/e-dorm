package com.edorm.controllers.rooms;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.rooms.AddRoomRequest;
import com.edorm.models.rooms.GetRoomResponse;
import com.edorm.services.rooms.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.ROOM)
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{roomNumber}")
    public ResponseEntity<GetRoomResponse> getRoom(@PathVariable String roomNumber) {
        return ResponseEntity.ok(roomService.getRoomInfo(roomNumber));
    }

    @GetMapping
    public ResponseEntity<List<GetRoomResponse>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addRoom(@RequestBody AddRoomRequest request) {
        roomService.addRoom(request);
    }

}
