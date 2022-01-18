package com.edorm.services.messages;

import com.edorm.entities.images.Image;
import com.edorm.entities.messages.Conversation;
import com.edorm.entities.messages.Message;
import com.edorm.entities.users.User;
import com.edorm.models.messages.GetMessageResponse;
import com.edorm.repositories.messages.MessageRepository;
import com.edorm.services.images.ImageService;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ImageService imageService;
    private final UserService userService;
    private final ConversationService conversationService;

    public void addMessage(String content, MultipartFile file, long conversationId, long userId) {
        final User sender = userService.getUser(userId);
        final Image image = imageService.addImage(file);
        final Conversation conversation = conversationService.getConversation(conversationId);

        Message message = new Message();
        message.setContent(content);
        message.setImage(image);
        message.setCreateDate(LocalDateTime.now());
        message.setSender(sender);
        message.setConversation(conversation);
        messageRepository.save(message);
    }

    @Transactional
    public List<GetMessageResponse> getMessages(long conversationId, long userId) {
        final Conversation conversation = conversationService.getConversation(conversationId);

        List<GetMessageResponse> messages = messageRepository.findTop20ByConversationOrderByCreateDateDesc(conversation)
                .stream()
                .map(message -> mapMessageToMessageResponse(message, userId))
                .collect(Collectors.toList());
        Collections.reverse(messages);
        return messages;
    }

    private GetMessageResponse mapMessageToMessageResponse(Message message, long userId) {
        final byte[] image = Objects.nonNull(message.getImage()) ? message.getImage().getContent() : null;

        GetMessageResponse getMessageResponse = new GetMessageResponse();
        getMessageResponse.setCreateDate(message.getCreateDate());
        getMessageResponse.setContent(message.getContent());
        getMessageResponse.setSentByUser(message.getSender().getId() == userId);
        getMessageResponse.setImage(image);
        return getMessageResponse;
    }

}
