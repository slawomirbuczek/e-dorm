package com.edorm.repositories.messages;

import com.edorm.entities.messages.Message;
import com.edorm.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByFromAndTo(User from, User to);

}
