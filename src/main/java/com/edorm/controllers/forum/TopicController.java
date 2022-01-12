package com.edorm.controllers.forum;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.forum.GetTopicResponse;
import com.edorm.models.forum.UpdateTopicRequest;
import com.edorm.services.forum.TopicService;
import com.edorm.utils.PrincipalUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.TOPIC)
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<GetTopicResponse>> getTopics() {
        return ResponseEntity.ok(
                topicService.getTopics()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addTopic(@RequestPart String content,
                         @RequestPart MultipartFile file,
                         Principal principal) {
        topicService.addTopic(content, file, PrincipalUtil.getUserId(principal));
    }

    @PutMapping("/{topicId}/content")
    @ResponseStatus(HttpStatus.OK)
    public void updateTopicContent(@RequestBody UpdateTopicRequest request,
                                   @PathVariable Long topicId,
                                   Principal principal) {
        topicService.updateTopic(request, null, topicId, PrincipalUtil.getUserId(principal));
    }

    @PutMapping("/{topicId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void updateTopicImage(@RequestPart MultipartFile image,
                                 @PathVariable Long topicId,
                                 Principal principal) {
        topicService.updateTopic(null, image, topicId, PrincipalUtil.getUserId(principal));
    }

    @DeleteMapping("/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTopic(@PathVariable Long topicId, Principal principal) {
        topicService.deleteTopic(topicId, PrincipalUtil.getUserId(principal));
    }

}
