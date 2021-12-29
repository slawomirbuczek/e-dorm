package com.edorm.controllers.tickets;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.tickets.*;
import com.edorm.services.tickets.TicketService;
import com.edorm.utils.PrincipalUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.TICKET)
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<AddTicketResponse> addTicket(@RequestBody AddTicketRequest request, Principal principal) {
        AddTicketResponse response = ticketService.addTicket(request, PrincipalUtil.getUserId(principal));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetTicketResponse>> getTickets(Principal principal) {
        List<GetTicketResponse> response = ticketService.getTickets(PrincipalUtil.getUserId(principal));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public void closeTicket(@PathVariable long ticketId, Principal principal) {
        ticketService.closeTicket(ticketId, PrincipalUtil.getUserId(principal));
    }

}
