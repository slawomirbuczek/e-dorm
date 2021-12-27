package com.edorm.controllers.messages;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.messages.AddContentMessageRequest;
import com.edorm.models.messages.GetMessageResponse;
import com.edorm.services.messages.MessageService;
import com.edorm.utils.PrincipalUtil;
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

    @PostMapping("/content/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    public void addContentMessage(@RequestBody AddContentMessageRequest request,
                                  @PathVariable long conversationId,
                                  Principal principal) {
        messageService.addContentMessage(request, conversationId, PrincipalUtil.getUserId(principal));
    }

    @PostMapping("/image/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    public void addImageMessage(@RequestPart MultipartFile image,
                                @PathVariable long conversationId,
                                Principal principal) {
        messageService.addImageMessage(image, conversationId, PrincipalUtil.getUserId(principal));
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<GetMessageResponse>> getMessages(@PathVariable long conversationId, Principal principal) {
        return ResponseEntity.ok(messageService.getMessages(conversationId, PrincipalUtil.getUserId(principal)));
    }

}
