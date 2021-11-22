package com.edorm.controllers.messages;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.messages.GetMessageResponse;
import com.edorm.services.messages.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.MESSAGE)
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addMessage(@RequestPart(required = false) MultipartFile image,
                           @RequestPart String content,
                           @PathVariable Long userId,
                           Principal principal) {
        messageService.addMessage(image, content, userId, Long.parseLong(principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<GetMessageResponse>> getMessages(Principal principal) {
        return ResponseEntity.ok(messageService.getMessages(Long.parseLong(principal.getName())));
    }

}
