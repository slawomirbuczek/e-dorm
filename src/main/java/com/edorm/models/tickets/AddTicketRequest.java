package com.edorm.models.tickets;

import com.edorm.enums.tickets.TicketType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTicketRequest {

    private TicketType type;

    private String subject;

}
