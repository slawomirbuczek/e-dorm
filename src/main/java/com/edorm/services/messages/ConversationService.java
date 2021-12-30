package com.edorm.services.messages;

import com.edorm.entities.messages.Conversation;
import com.edorm.entities.users.User;
import com.edorm.models.messages.AddConversationResponse;
import com.edorm.models.messages.GetConversationResponse;
import com.edorm.models.messages.GetNewConversationResponse;
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

    public AddConversationResponse addConversation(long userId, long userTwoId) {
        User user = userService.getUser(userId);
        User userTwo = userService.getUser(userTwoId);

        Conversation conversation = new Conversation();
        conversation.setUpdateDate(LocalDateTime.now());
        conversation.setUserOne(user);
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
                                conversation,
                                Objects.equals(conversation.getUserOne().getId(), user.getId()) ?
                                        conversation.getUserTwo() : conversation.getUserOne()
                        )
                )
                .collect(Collectors.toList());
    }

    public List<GetNewConversationResponse> getNewConversationResponses(long userId) {
        List<Long> userTwoIds = getConversationsByUserId(userId).stream()
                .map(conversation -> {
                    Long userOneId = conversation.getUserOne().getId();
                    Long userTwoId = conversation.getUserTwo().getId();
                    return Objects.equals(userOneId, userId) ? userTwoId : userOneId;
                })
                .collect(Collectors.toList());
        userTwoIds.add(userId);

        List<User> users = userService.getUsersWithIdNotInList(userTwoIds);

        return users.stream().map(this::mapUserToNewConversationResponse).collect(Collectors.toList());
    }

    public List<Conversation> getConversationsByUserId(long userId) {
        User user = userService.getUser(userId);
        return conversationRepository.findAllByUserOneOrUserTwoOrderByUpdateDateAsc(user, user);
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

    private GetNewConversationResponse mapUserToNewConversationResponse(User user) {
        GetNewConversationResponse response = new GetNewConversationResponse();
        response.setId(user.getId());
        response.setName(user.getFirstName() + " " + user.getLastName());
        return response;
    }

}
