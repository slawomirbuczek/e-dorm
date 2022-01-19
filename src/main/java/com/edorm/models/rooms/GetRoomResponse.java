package com.edorm.models.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRoomResponse {

    private String roomNumber;

    private int floor;

    private int size;

    private int residents;

    private String compositionNumber;

}
