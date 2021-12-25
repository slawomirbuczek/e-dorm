package com.edorm.repositories.messages;

import com.edorm.entities.messages.Conversation;
import com.edorm.entities.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findTop20ByConversationOrderByCreateDateDesc(Conversation conversation);


}
