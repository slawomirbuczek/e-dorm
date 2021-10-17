package com.edorm.controllers.rooms;

import com.edorm.controllers.RestEndpoint;
import com.edorm.entities.rooms.Room;
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

    @GetMapping("/{number}")
    public ResponseEntity<Room> getRoom(@PathVariable String number) {
        return ResponseEntity.ok(roomService.getRoom(number));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    @DeleteMapping("/{number}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoom(@PathVariable String number) {
        roomService.deleteRoom(number);
    }

}
