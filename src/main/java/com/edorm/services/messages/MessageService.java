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

    public void addContentMessage(String content, long conversationId, long userId) {
        final User sender = userService.getUser(userId);
        final Conversation conversation = conversationService.getConversation(conversationId);

        Message message = new Message();
        message.setCreateDate(LocalDateTime.now());
        message.setContent(content);
        message.setImage(null);
        message.setSender(sender);
        message.setConversation(conversation);
        messageRepository.save(message);
    }

    public void addImageMessage(MultipartFile image, long conversationId, long userId) {
        final Image messageImage = imageService.addImage(image);
        final User sender = userService.getUser(userId);
        final Conversation conversation = conversationService.getConversation(conversationId);

        Message message = new Message();
        message.setCreateDate(LocalDateTime.now());
        message.setContent(null);
        message.setImage(messageImage);
        message.setSender(sender);
        message.setConversation(conversation);
        messageRepository.save(message);
    }


    @Transactional
    public List<GetMessageResponse> getMessages(long conversationId, long userId) {
        final Conversation conversation = conversationService.getConversation(conversationId);

        return messageRepository.findTop100ByConversationOrderByCreateDateAsc(conversation).stream()
                .map(message -> mapMessageToMessageResponse(message, userId))
                .collect(Collectors.toList());
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
