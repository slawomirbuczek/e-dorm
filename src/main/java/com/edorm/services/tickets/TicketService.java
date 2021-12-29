package com.edorm.services.tickets;

import com.edorm.entities.tickets.Ticket;
import com.edorm.entities.users.User;
import com.edorm.models.tickets.*;
import com.edorm.repositories.tickets.TicketRepository;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;

    public AddTicketResponse addTicket(AddTicketRequest request, long userId) {
        final User user = userService.getUser(userId);

        Ticket ticket = new Ticket();
        ticket.setCreateDate(LocalDateTime.now());
        ticket.setType(request.getType());
        ticket.setSubject(request.getSubject());
        ticket.setOpen(true);
        ticket.setUser(user);
        ticket = ticketRepository.save(ticket);

        return new AddTicketResponse(ticket.getId());
    }

    public List<GetTicketResponse> getTickets(long userId) {
        final User user = userService.getUser(userId);

        return ticketRepository.findAllByUserOrderByCreateDateDesc(user).stream()
                .map(this::mapTicketToResponse)
                .collect(Collectors.toList());
    }

    public void closeTicket(long ticketId, long userId) {
        Ticket ticket = getTicket(ticketId);

        if (Objects.equals(ticket.getUser().getId(), userId)) {
            ticket.setOpen(false);
            ticketRepository.save(ticket);
        }
    }

    protected Ticket getTicket(long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NullPointerException("Ticket not found"));
    }


    private GetTicketResponse mapTicketToResponse(Ticket ticket) {
        GetTicketResponse response = new GetTicketResponse();
        response.setId(ticket.getId());
        response.setType(ticket.getType());
        response.setSubject(ticket.getSubject());
        response.setCreateDate(ticket.getCreateDate());
        response.setOpen(ticket.getOpen());
        return response;
    }

}
