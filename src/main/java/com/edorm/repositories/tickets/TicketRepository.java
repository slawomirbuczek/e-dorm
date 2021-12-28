package com.edorm.repositories.tickets;

import com.edorm.entities.tickets.Ticket;
import com.edorm.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUserOrderByCreateDateDesc(User user);

}
