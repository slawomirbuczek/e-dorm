package com.edorm.services.forum;

import com.edorm.entities.forum.Post;
import com.edorm.entities.images.Image;
import com.edorm.entities.users.User;
import com.edorm.models.forum.*;
import com.edorm.repositories.forum.PostRepository;
import com.edorm.services.images.ImageService;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final UserService userService;

    public void addPost(AddPostRequest request, Long topicId, long userId) {
        Image image = saveImage(request.getImage());
        User user = userService.getUser(userId);
        Post topic = getTopic(topicId);

        Post post = new Post();
        post.setCreateDate(LocalDateTime.now());
        post.setEdited(false);
        post.setDeleted(false);
        post.setContent(request.getContent());
        post.setImage(image);
        post.setUser(user);
        post.setTopic(topic);
        postRepository.save(post);
    }

    //TODO pageable
    public List<GetPostResponse> getPosts() {
        List<GetPostResponse> posts = new ArrayList<>();

        List<Post> topics = postRepository.findAllByTopicIsNull();

        topics.forEach(topic -> {
            List<PostResponse> responses = postRepository.findAllByTopic(topic).stream()
                    .map(this::mapPostToResponse)
                    .collect(Collectors.toList());

            GetPostResponse getPostResponse = new GetPostResponse();
            getPostResponse.setTopic(mapPostToResponse(topic));
            getPostResponse.setResponses(responses);
            posts.add(getPostResponse);
        });

        return posts;
    }

    public void updatePostContent(long postId, String content) {
        Post post = getPost(postId);
        post.setContent(content);
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

    private Image saveImage(MultipartFile multipartFile) {
        return Objects.isNull(multipartFile) || multipartFile.isEmpty()
                ? null
                : imageService.addImage(multipartFile);
    }

    private Post getTopic(Long topicId) {
        return Objects.nonNull(topicId) ? getPost(topicId) : null;
    }

    private PostResponse mapPostToResponse(Post post) {
        String fullName = post.getUser().getFirstName() + " " + post.getUser().getLastName();
        byte[] image = Objects.nonNull(post.getImage())
                ? post.getImage().getContent()
                : null;
        byte[] photo = Objects.nonNull(post.getUser().getPhoto())
                ? post.getUser().getPhoto().getContent()
                : null;

        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setFullName(fullName);
        response.setEdited(post.getEdited());
        response.setDate(post.getCreateDate());
        response.setContent(post.getContent());
        response.setImage(image);
        response.setPhoto(photo);
        return response;
    }

}
