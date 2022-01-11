package com.edorm.repositories.forum;

import com.edorm.entities.forum.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByOrderByCreateDateDesc();

}
