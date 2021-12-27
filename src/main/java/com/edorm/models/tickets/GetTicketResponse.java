package com.edorm.models.tickets;

import com.edorm.enums.tickets.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketResponse {

    private long id;

    private TicketType type;

    private String subject;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

    private Boolean open;

}
