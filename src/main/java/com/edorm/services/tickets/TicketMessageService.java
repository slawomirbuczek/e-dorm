package com.edorm.services.tickets;

import com.edorm.entities.images.Image;
import com.edorm.entities.tickets.Ticket;
import com.edorm.entities.tickets.TicketMessage;
import com.edorm.models.tickets.GetTicketMessageResponse;
import com.edorm.repositories.tickets.TicketMessageRepository;
import com.edorm.services.images.ImageService;
import com.edorm.services.images.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketMessageService {

    private final TicketMessageRepository ticketMessageRepository;
    private final TicketService ticketService;
    private final ImageService imageService;

    public void addTicketMessage(String content, MultipartFile file, long ticketId, long userId) {
        final Ticket ticket = ticketService.getTicket(ticketId);

        if (!ticket.getOpen()) {
            return;
        }

        final Image image = imageService.addImage(file);

        if (Objects.equals(ticket.getUser().getId(), userId)) {
            TicketMessage ticketMessage = new TicketMessage();
            ticketMessage.setTicket(ticket);
            ticketMessage.setContent(content);
            ticketMessage.setCreateDate(LocalDateTime.now());
            ticketMessage.setImage(image);
            ticketMessage.setSentByUser(true);

            ticketMessageRepository.save(ticketMessage);
        }
    }

    @Transactional
    public List<GetTicketMessageResponse> getTicketMessages(long ticketId, long userId) {
        final Ticket ticket = ticketService.getTicket(ticketId);

        return Objects.equals(ticket.getUser().getId(), userId) ?
                ticketMessageRepository.findAllByTicketOrderByCreateDateAsc(ticket).stream()
                        .map(this::mapTicketMessageToResponse)
                        .collect(Collectors.toList())
                : null;

    }

    public GetTicketMessageResponse mapTicketMessageToResponse(TicketMessage ticketMessage) {
        GetTicketMessageResponse response = new GetTicketMessageResponse();
        response.setId(ticketMessage.getId());
        response.setContent(ticketMessage.getContent());
        response.setCreateDate(ticketMessage.getCreateDate());
        response.setSentByUser(ticketMessage.isSentByUser());
        response.setImage(ImageUtil.getImageContent(ticketMessage.getImage()));
        return response;
    }


}
