package com.edorm.services.forum;

import com.edorm.entities.forum.Post;
import com.edorm.repositories.forum.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TopicService topicService;

    public void addPost(Post post, long topicId) {
        post = postRepository.save(post);
        topicService.addPostToTopic(post, topicId);
    }

    public void editPost(long postId, String content) {
        Post post = getPost(postId);
        post.setContent(content);
        post.setUpdateDate(LocalDateTime.now());
        post.setEdited(true);
        updatePost(post);
    }

    public void deletePost(long postId) {
        Post post = getPost(postId);
        post.setDeleted(true);
        updatePost(post);
    }

    private Post getPost(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NullPointerException::new);
    }

    private void updatePost(Post post) {
        postRepository.save(post);
    }

}
