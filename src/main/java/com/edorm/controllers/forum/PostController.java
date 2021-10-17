package com.edorm.controllers.forum;

import com.edorm.controllers.RestEndpoint;
import com.edorm.entities.forum.Post;
import com.edorm.services.forum.PostService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController(RestEndpoint.POST)
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{topicId}")
    public void addPost(@PathVariable Long topicId, @RequestBody Post post) {
        postService.addPost(post, topicId);
    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody String content) {
        postService.editPost(postId, content);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

}
