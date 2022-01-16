package com.edorm.services.reservations;

import com.edorm.entities.reservations.Reservation;
import com.edorm.entities.users.User;
import com.edorm.enums.reservations.ReservationType;
import com.edorm.exceptions.reservation.ReservationNotAvailableException;
import com.edorm.models.reservations.GetReservationHistoryResponse;
import com.edorm.models.reservations.GetReservationResponse;
import com.edorm.repositories.reservations.ReservationRepository;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;

    public void reserve(long reservationId, long userId) {
        final Reservation reservation = getReservation(reservationId);

        if (Objects.nonNull(reservation.getUser())) {
            throw new ReservationNotAvailableException();
        }

        final User user = userService.getUser(userId);

        reservation.setUser(user);
        reservationRepository.save(reservation);
    }

    public List<GetReservationResponse> getReservations(long plusDays, ReservationType type, long userId) {
        final LocalDate date = LocalDate.now(ZoneId.of("Poland")).plusDays(plusDays);
        if (!reservationExists(date, type)) {
            createReservations(date, type);
        }
        return reservationRepository.findAllByDateAndTypeOrderByTime(date, type).stream()
                .map(reservation -> mapReservationToResponse(reservation, userId))
                .collect(Collectors.toList());
    }

    public List<GetReservationHistoryResponse> getReservationHistory(ReservationType type, long userId) {
        final User user = userService.getUser(userId);

        return reservationRepository.findAllByUserAndTypeOrderByDateDesc(user, type).stream()
                .map(this::mapReservationHistoryToResponse)
                .collect(Collectors.toList());
    }

    private void createReservations(LocalDate date, ReservationType type) {
        List<Reservation> reservations = new ArrayList<>();
        for (
                LocalTime time = type.getStartTime();
                time.isBefore(type.getEndTime());
                time = time.plusHours(type.getInterval()).isAfter(time) ? time.plusHours(type.getInterval()) : type.getEndTime()
        ) {
            Reservation reservation = new Reservation();
            reservation.setType(type);
            reservation.setDate(date);
            reservation.setTime(time);
            reservation.setUser(null);
            reservations.add(reservation);
        }
        reservationRepository.saveAll(reservations);
    }


    private boolean reservationExists(LocalDate date, ReservationType type) {
        return reservationRepository.existsByDateAndType(date, type);
    }

    private Reservation getReservation(long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(NullPointerException::new);
    }

    private GetReservationResponse mapReservationToResponse(Reservation reservation, long userId) {
        final boolean available = Objects.isNull(reservation.getUser());
        final boolean reservedByUser = Objects.nonNull(reservation.getUser()) && reservation.getUser().getId() == userId;
        final int interval = reservation.getType().getInterval();

        GetReservationResponse response = new GetReservationResponse();
        response.setId(reservation.getId());
        response.setStartTime(reservation.getTime());
        response.setEndTime(reservation.getTime().plusHours(interval));
        response.setAvailable(available);
        response.setReservedByUser(reservedByUser);
        return response;
    }

    private GetReservationHistoryResponse mapReservationHistoryToResponse(Reservation reservation) {
        final int interval = reservation.getType().getInterval();

        GetReservationHistoryResponse response = new GetReservationHistoryResponse();
        response.setDate(reservation.getDate());
        response.setStartTime(reservation.getTime());
        response.setEndTime(reservation.getTime().plusHours(interval));
        return response;
    }

}
