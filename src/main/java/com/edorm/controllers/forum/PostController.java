package com.edorm.controllers.forum;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.forum.AddPostRequest;
import com.edorm.models.forum.AddPostResponse;
import com.edorm.models.forum.GetPostResponse;
import com.edorm.models.forum.UpdateTopicRequest;
import com.edorm.services.forum.PostService;
import com.edorm.utils.PrincipalUtil;
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

    @GetMapping("/{topicId}")
    public ResponseEntity<List<GetPostResponse>> getPosts(@PathVariable Long topicId) {
        return ResponseEntity.ok(
                postService.getPosts(topicId)
        );
    }

    @PostMapping("/{topicId}/content")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AddPostResponse> addPostContent(@RequestBody AddPostRequest request,
                                                          @PathVariable Long topicId,
                                                          Principal principal) {
        return ResponseEntity.ok(
                postService.addPost(request, null, topicId, PrincipalUtil.getUserId(principal))
        );
    }

    @PostMapping("/{topicId}/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AddPostResponse> addPostImage(@RequestPart MultipartFile image,
                                                        @PathVariable Long topicId,
                                                        Principal principal) {
        return ResponseEntity.ok(
                postService.addPost(null, image, topicId, PrincipalUtil.getUserId(principal))
        );
    }

    @PutMapping("/{postId}/content")
    public void updatePostContent(@RequestBody UpdateTopicRequest request,
                                  @PathVariable Long postId,
                                  Principal principal) {
        postService.updatePost(request, null, postId, PrincipalUtil.getUserId(principal));
    }

    @PutMapping("/{postId}/image")
    public void updatePostImage(@RequestPart MultipartFile image,
                                @PathVariable Long postId,
                                Principal principal) {
        postService.updatePost(null, image, postId, PrincipalUtil.getUserId(principal));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId, Principal principal) {
        postService.deletePost(postId, PrincipalUtil.getUserId(principal));
    }

}
