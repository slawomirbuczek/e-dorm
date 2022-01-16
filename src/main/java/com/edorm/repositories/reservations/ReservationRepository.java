package com.edorm.repositories.reservations;

import com.edorm.entities.reservations.Reservation;
import com.edorm.entities.users.User;
import com.edorm.enums.reservations.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByDateAndTypeOrderByTime(LocalDate date, ReservationType type);

    boolean existsByDateAndType(LocalDate date, ReservationType type);

    List<Reservation> findAllByUserAndTypeOrderByDateDesc(User user, ReservationType type);

}
