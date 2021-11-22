package com.edorm.services.messages;

import com.edorm.entities.images.Image;
import com.edorm.entities.messages.Message;
import com.edorm.entities.users.User;
import com.edorm.models.messages.GetMessageResponse;
import com.edorm.models.messages.MessageResponse;
import com.edorm.models.users.GetUserResponse;
import com.edorm.repositories.messages.MessageRepository;
import com.edorm.services.images.ImageService;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ImageService imageService;
    private final UserService userService;

    public void addMessage(MultipartFile image, String content, long toId, long userId) {
        Image messageImage = saveImage(image);
        User from = userService.getUser(userId);
        User to = userService.getUser(toId);

        Message message = new Message();
        message.setCreateDate(LocalDateTime.now());
        message.setContent(content);
        message.setImage(messageImage);
        message.setFrom(from);
        message.setTo(to);
        messageRepository.save(message);
    }

    @Transactional
    public List<GetMessageResponse> getMessages(long userId) {
        User user = userService.getUser(userId);

        List<Message> messages = messageRepository.findAllByFromAndTo(user, user);
        return aggregateMessages(messages, userId);
    }

    private List<GetMessageResponse> aggregateMessages(List<Message> messages, long userId) {
        Map<User, List<Message>> messagesByUser = new HashMap<>();

        messages.forEach(message -> {
            final long fromId = message.getFrom().getId();
            User user = fromId == userId ? message.getTo() : message.getFrom();
            List<Message> mapMessages = messagesByUser.getOrDefault(user, new ArrayList<>());
            mapMessages.add(message);
            messagesByUser.put(user, mapMessages);
        });

        return messagesByUser.entrySet().stream().map(
                entry -> mapMessageToGetMessageResponse(entry.getValue(), entry.getKey())
        ).collect(Collectors.toList());
    }

    private GetMessageResponse mapMessageToGetMessageResponse(List<Message> messages, User user) {
        GetUserResponse getUserResponse = userService.mapUserToGetUserResponse(user);
        List<MessageResponse> messageResponses = messages.stream()
                .map(this::mapMessageToMessageResponse)
                .collect(Collectors.toList());

        GetMessageResponse getMessageResponse = new GetMessageResponse();
        getMessageResponse.setUser(getUserResponse);
        getMessageResponse.setMessages(messageResponses);
        return getMessageResponse;
    }

    private MessageResponse mapMessageToMessageResponse(Message message) {
        byte[] image = Objects.nonNull(message.getImage()) ? message.getImage().getContent() : null;

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setCreateDate(message.getCreateDate());
        messageResponse.setContent(message.getContent());
        messageResponse.setImage(image);
        return messageResponse;
    }

    private Image saveImage(MultipartFile multipartFile) {
        return Objects.isNull(multipartFile) || multipartFile.isEmpty()
                ? null
                : imageService.addImage(multipartFile);
    }

}
