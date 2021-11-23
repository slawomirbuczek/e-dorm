package com.edorm.controllers.messages;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.messages.GetConversationResponse;
import com.edorm.services.messages.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.CONVERSATION)
@AllArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping
    public ResponseEntity<List<GetConversationResponse>> getConversations(Principal principal) {
        return ResponseEntity.ok(conversationService.getConversations(Long.parseLong(principal.getName())));
    }

    @PostMapping("/{recipientId}")
    @ResponseStatus(HttpStatus.OK)
    public void addConversation(@PathVariable long recipientId, Principal principal) {
        conversationService.addConversation(Long.parseLong(principal.getName()), recipientId);
    }


}
