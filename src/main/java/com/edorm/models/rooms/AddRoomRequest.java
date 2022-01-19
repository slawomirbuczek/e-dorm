package com.edorm.models.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomRequest {

    private String roomNumber;

    private int floor;

    private int size;

    private String compositionNumber;

}
