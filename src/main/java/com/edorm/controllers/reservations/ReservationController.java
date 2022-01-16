package com.edorm.controllers.reservations;

import com.edorm.controllers.RestEndpoint;
import com.edorm.enums.reservations.ReservationType;
import com.edorm.models.reservations.GetReservationHistoryResponse;
import com.edorm.models.reservations.GetReservationResponse;
import com.edorm.services.reservations.ReservationService;
import com.edorm.utils.PrincipalUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.RESERVATION)
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{type}/{plusDays}")
    public ResponseEntity<List<GetReservationResponse>> getReservations(
            @PathVariable Long plusDays,
            @PathVariable ReservationType type,
            Principal principal) {
        return ResponseEntity.ok(
                reservationService.getReservations(plusDays, type, PrincipalUtil.getUserId(principal))
        );
    }

    @PostMapping("/{reservationId}")
    public void reserve(@PathVariable long reservationId, Principal principal) {
        reservationService.reserve(reservationId, PrincipalUtil.getUserId(principal));
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<GetReservationHistoryResponse>> getReservationHistory(@PathVariable ReservationType type, Principal principal) {
        return ResponseEntity.ok(
                reservationService.getReservationHistory(type, PrincipalUtil.getUserId(principal))
        );
    }

}
