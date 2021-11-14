package com.edorm.controllers.forum;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.forum.AddPostRequest;
import com.edorm.models.forum.GetPostResponse;
import com.edorm.services.forum.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.POST)
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<GetPostResponse>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addNewPost(@RequestPart(required = false) MultipartFile image,
                           @RequestPart String content,
                           Principal principal) {
        postService.addPost(new AddPostRequest(content, image), null, Long.parseLong(principal.getName()));
    }

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void addPost(@RequestPart(required = false) MultipartFile image,
                        @RequestPart String content,
                        @PathVariable Long postId,
                        Principal principal) {
        postService.addPost(new AddPostRequest(content, image), postId, Long.parseLong(principal.getName()));
    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody String content) {
        postService.updatePostContent(postId, content);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

}
