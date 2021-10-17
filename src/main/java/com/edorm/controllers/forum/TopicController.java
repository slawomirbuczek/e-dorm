package com.edorm.controllers.forum;

import com.edorm.controllers.RestEndpoint;
import com.edorm.entities.forum.Topic;
import com.edorm.services.forum.TopicService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.TOPIC)
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<Topic>> getTopics() {
        return ResponseEntity.ok(topicService.getTopics());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
    }

    @PutMapping("/{topicId}")
    public void updateTopic(@PathVariable Long topicId, @RequestBody String content) {
        topicService.updateTopicContent(topicId, content);
    }

    @DeleteMapping("/{topicId}")
    public void deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
    }

}
