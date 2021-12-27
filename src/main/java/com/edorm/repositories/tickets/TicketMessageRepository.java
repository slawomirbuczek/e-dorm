package com.edorm.repositories.tickets;

import com.edorm.entities.tickets.Ticket;
import com.edorm.entities.tickets.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long> {

    List<TicketMessage> findAllByTicketOrderByCreateDateAsc(Ticket ticket);

}
