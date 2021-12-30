package com.edorm.controllers.messages;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.messages.AddConversationResponse;
import com.edorm.models.messages.GetConversationResponse;
import com.edorm.models.messages.GetNewConversationResponse;
import com.edorm.services.messages.ConversationService;
import com.edorm.utils.PrincipalUtil;
import lombok.AllArgsConstructor;
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
        List<GetConversationResponse> response = conversationService.getConversations(PrincipalUtil.getUserId(principal));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{recipientId}")
    public ResponseEntity<AddConversationResponse> addConversation(@PathVariable long recipientId, Principal principal) {
        AddConversationResponse response = conversationService.addConversation(PrincipalUtil.getUserId(principal), recipientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetNewConversationResponse>> getNewConversations(Principal principal) {
        return ResponseEntity.ok(
                conversationService.getNewConversationResponses(PrincipalUtil.getUserId(principal))
        );
    }


}
