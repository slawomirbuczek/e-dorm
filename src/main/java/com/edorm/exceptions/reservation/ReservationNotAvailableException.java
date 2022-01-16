package com.edorm.exceptions.reservation;

public class ReservationNotAvailableException extends RuntimeException {
    public ReservationNotAvailableException() {
        super("Reservation unavailable");
    }
}
