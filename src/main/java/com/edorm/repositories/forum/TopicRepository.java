package com.edorm.repositories.forum;

import com.edorm.entities.forum.Topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository <Topic, Long> {

}
