package com.edorm.controllers.tickets;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.tickets.AddTicketMessageRequest;
import com.edorm.models.tickets.GetTicketMessageResponse;
import com.edorm.services.tickets.TicketMessageService;
import com.edorm.utils.PrincipalUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.TICKET_MESSAGE)
@AllArgsConstructor
public class TicketMessageController {

    private final TicketMessageService ticketMessageService;

    @PostMapping("/{ticketId}/content")
    @ResponseStatus(HttpStatus.OK)
    public void addTicketMessageContent(@RequestBody AddTicketMessageRequest request,
                                        @PathVariable long ticketId,
                                        Principal principal) {
        ticketMessageService.addTicketMessage(request, null, ticketId, PrincipalUtil.getUserId(principal));
    }

    @PostMapping("/{ticketId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void addTicketMessageImage(@RequestPart MultipartFile file,
                                      @PathVariable long ticketId,
                                      Principal principal) {
        ticketMessageService.addTicketMessage(null, file, ticketId, PrincipalUtil.getUserId(principal));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<GetTicketMessageResponse>> getTicketMessages(@PathVariable long ticketId,
                                                                            Principal principal) {
        return ResponseEntity.ok(
                ticketMessageService.getTicketMessages(ticketId, PrincipalUtil.getUserId(principal))
        );
    }

}
