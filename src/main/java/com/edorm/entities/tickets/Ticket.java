package com.edorm.entities.tickets;


import com.edorm.entities.users.User;
import com.edorm.enums.tickets.TicketType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "open", nullable = false)
    private Boolean open;

    @OneToOne
    private User user;

}
