package com.edorm.controllers.tickets;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.tickets.AddTicketRequest;
import com.edorm.models.tickets.GetTicketResponse;
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
    @ResponseStatus(HttpStatus.OK)
    public void addTicket(@RequestBody AddTicketRequest request, Principal principal) {
        ticketService.addTicket(request, PrincipalUtil.getUserId(principal));
    }

    @GetMapping
    public ResponseEntity<List<GetTicketResponse>> getTickets(Principal principal) {
        return ResponseEntity.ok(ticketService.getTickets(PrincipalUtil.getUserId(principal)));
    }

    @PutMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public void closeTicket(@PathVariable long ticketId, Principal principal) {
        ticketService.closeTicket(ticketId, PrincipalUtil.getUserId(principal));
    }

}
