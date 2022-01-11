package com.edorm.services.messages;

import com.edorm.entities.messages.Conversation;
import com.edorm.entities.messages.Message;
import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.models.messages.AddMessageRequest;
import com.edorm.repositories.messages.MessageRepository;
import com.edorm.services.images.ImageService;
import com.edorm.services.users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private UserService userService;

    @Mock
    private ConversationService conversationService;

    @InjectMocks
    private MessageService messageService;

    @Captor
    ArgumentCaptor<Message> messageCaptor;

    private final static long userId = 1L;
    private final static Role userRole = Role.BASIC;

    private final static long conversationId = 1L;

    private final static String messageContent = "Message content";

    @Test
    void shouldAddMessageWhenMultipartFileIsNull() {
        given(userService.getUser(userId)).willReturn(getMockUser());
        given(imageService.addImage(null)).willReturn(null);
        given(conversationService.getConversation(conversationId)).willReturn(getMockConversation());

        AddMessageRequest addMessageRequest = new AddMessageRequest(messageContent);

        messageService.addMessage(addMessageRequest, null, conversationId, userId);

        verify(userService, times(1)).getUser(userId);
        verify(imageService, times(1)).addImage(null);
        verify(conversationService, times(1)).getConversation(conversationId);
        verify(messageRepository, times(1)).save(messageCaptor.capture());

        Message message = messageCaptor.getValue();

        assertThat(message.getConversation().getId()).isEqualTo(conversationId);
        assertThat(message.getImage()).isNull();
        assertThat(message.getContent()).isEqualTo(messageContent);
        assertThat(message.getSender().getId()).isEqualTo(userId);
        assertThat(message.getSender().getRole()).isEqualTo(userRole);

    }

    private User getMockUser() {
        User user = new User();
        user.setId(userId);
        user.setRole(userRole);
        return user;
    }

    private Conversation getMockConversation() {
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        return conversation;
    }

}