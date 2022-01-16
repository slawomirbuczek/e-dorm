package com.edorm.enums.reservations;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public enum ReservationType {

    BILLIARD(LocalTime.of(8, 0), LocalTime.of(23, 0), 3),
    TV(LocalTime.of(8, 0), LocalTime.of(23, 0), 3),
    LAUNDRY(LocalTime.MIN, LocalTime.MAX, 3);

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int interval;

}
