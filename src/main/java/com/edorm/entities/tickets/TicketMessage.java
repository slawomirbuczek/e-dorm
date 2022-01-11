package com.edorm.entities.tickets;

import com.edorm.entities.images.Image;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TICKET_MESSAGE")
public class TicketMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne
    private Image image;

    @Column(name = "sent_by_user", nullable = false)
    private boolean sentByUser;

    @OneToOne
    private Ticket ticket;


}
