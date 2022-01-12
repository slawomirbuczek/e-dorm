package com.edorm.services.forum;

import com.edorm.entities.forum.Post;
import com.edorm.entities.forum.Topic;
import com.edorm.entities.images.Image;
import com.edorm.entities.users.User;
import com.edorm.models.forum.AddPostRequest;
import com.edorm.models.forum.AddPostResponse;
import com.edorm.models.forum.GetPostResponse;
import com.edorm.models.forum.UpdateTopicRequest;
import com.edorm.repositories.forum.PostRepository;
import com.edorm.services.images.ImageService;
import com.edorm.services.images.ImageUtil;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TopicService topicService;
    private final ImageService imageService;
    private final UserService userService;

    public AddPostResponse addPost(AddPostRequest request, long topicId, long userId) {
        final User user = userService.getUser(userId);
        final Image image = imageService.addImage(request.getImage());
        final Topic topic = topicService.getTopic(topicId);

        Post post = new Post();
        post.setContent(request.getContent());
        post.setImage(image);
        post.setCreateDate(LocalDateTime.now());
        post.setEdited(false);
        post.setUser(user);
        post.setTopic(topic);

        post = postRepository.save(post);

        return new AddPostResponse(post.getId());
    }

    public void updatePost(UpdateTopicRequest request, MultipartFile file, long postId, long userId) {
        final Post post = getPost(postId);

        if (!Objects.equals(post.getUser().getId(), userId)) {
            return;
        }

        if (Objects.nonNull(request)) {
            post.setContent(request.getContent());
            post.setEdited(true);
        }

        if (Objects.nonNull(file)) {
            Image image = imageService.addImage(file);
            post.setImage(image);
            post.setEdited(true);
        }

        postRepository.save(post);
    }

    public void deletePost(long postId, long userId) {
        final Post post = getPost(postId);

        if (Objects.equals(post.getUser().getId(), userId)) {
            postRepository.delete(post);
        }

    }

    public List<GetPostResponse> getPosts(long topicId) {
        final Topic topic = topicService.getTopic(topicId);

        return postRepository.findAllByTopicOrderByCreateDate(topic).stream()
                .map(this::mapPostToResponse)
                .collect(Collectors.toList());
    }

    private Post getPost(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("Post not found"));
    }

    private GetPostResponse mapPostToResponse(Post post) {
        final User user = post.getUser();

        GetPostResponse response = new GetPostResponse();
        response.setPostId(post.getId());
        response.setContent(post.getContent());
        response.setCreateDate(post.getCreateDate());
        response.setEdited(post.getEdited());
        response.setImage(ImageUtil.getImageContent(post.getImage()));
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setPhoto(ImageUtil.getImageContent(user.getPhoto()));
        return response;
    }

}
