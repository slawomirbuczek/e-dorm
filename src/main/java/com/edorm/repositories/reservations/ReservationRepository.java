package com.edorm.repositories.reservations;

import com.edorm.entities.reservations.Reservation;
import com.edorm.enums.reservations.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> getAllByDateAndTypeOrderByTime(LocalDate date, ReservationType type);

    boolean existsByDateAndType(LocalDate date, ReservationType type);

}
