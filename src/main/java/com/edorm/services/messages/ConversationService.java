package com.edorm.services.messages;

import com.edorm.entities.messages.Conversation;
import com.edorm.entities.users.User;
import com.edorm.models.messages.AddConversationResponse;
import com.edorm.models.messages.GetConversationResponse;
import com.edorm.repositories.messages.ConversationRepository;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserService userService;

    public AddConversationResponse addConversation(long userOneId, long userTwoId) {
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);

        Conversation conversation = new Conversation();
        conversation.setUpdateDate(LocalDateTime.now());
        conversation.setUserOne(userOne);
        conversation.setUserTwo(userTwo);
        conversation = conversationRepository.save(conversation);

        return new AddConversationResponse(conversation.getId());
    }

    public Conversation getConversation(long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NullPointerException("Conversation not found exception"));
    }

    public List<GetConversationResponse> getConversations(long userId) {
        User user = userService.getUser(userId);

        return conversationRepository.findAllByUserOneOrUserTwoOrderByUpdateDateAsc(user, user).stream()
                .map(conversation ->
                        mapConversationToResponse(
                                conversation, conversation.getUserOne().equals(user)
                                        ? conversation.getUserTwo()
                                        : conversation.getUserOne()
                        )
                )
                .collect(Collectors.toList());
    }

    private GetConversationResponse mapConversationToResponse(Conversation conversation, User recipient) {
        String fullName = recipient.getFirstName() + " " + recipient.getLastName();
        byte[] photo = Objects.nonNull(recipient.getPhoto()) ? recipient.getPhoto().getContent() : null;

        GetConversationResponse getConversationResponse = new GetConversationResponse();
        getConversationResponse.setConversationId(conversation.getId());
        getConversationResponse.setFullName(fullName);
        getConversationResponse.setPhoto(photo);
        return getConversationResponse;
    }


}
