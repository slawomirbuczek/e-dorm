package com.edorm.repositories.messages;

import com.edorm.entities.messages.Conversation;
import com.edorm.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findAllByUserOneOrUserTwoOrderByUpdateDateAsc(User userOne, User userTwo);


}
