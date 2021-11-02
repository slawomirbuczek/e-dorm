package com.edorm.services.forum;

import com.edorm.entities.forum.Post;
import com.edorm.entities.forum.Topic;
import com.edorm.repositories.forum.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    //TODO pageable
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    public void updateTopicContent(long topicId, String content) {
        Topic topic = getTopic(topicId);
        topic.setContent(content);
        topic.setEdited(true);
        topic.setUpdateDate(LocalDateTime.now());
        updateTopic(topic);
    }

    public void deleteTopic(long topicId) {
        Topic topic = getTopic(topicId);
        topic.setDeleted(true);
        updateTopic(topic);
    }

    protected void addPostToTopic(Post post, long topicId) {
        Topic topic = getTopic(topicId);
        topic.addPost(post);
        updateTopic(topic);
    }


    private Topic getTopic(long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(NullPointerException::new);
    }

    private void updateTopic(Topic topic) {
        topicRepository.save(topic);
    }

}
