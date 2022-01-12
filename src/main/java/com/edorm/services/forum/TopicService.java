package com.edorm.services.forum;

import com.edorm.entities.forum.Topic;
import com.edorm.entities.images.Image;
import com.edorm.entities.users.User;
import com.edorm.models.forum.GetTopicResponse;
import com.edorm.models.forum.UpdateTopicRequest;
import com.edorm.repositories.forum.TopicRepository;
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
public class TopicService {

    private final TopicRepository topicRepository;
    private final ImageService imageService;
    private final UserService userService;

    public void addTopic(String content, MultipartFile file, long userId) {
        final User user = userService.getUser(userId);
        final Image image = imageService.addImage(file);

        Topic topic = new Topic();
        topic.setContent(content);
        topic.setImage(image);
        topic.setCreateDate(LocalDateTime.now());
        topic.setEdited(false);
        topic.setCreator(user);

        topicRepository.save(topic);
    }

    public void updateTopic(UpdateTopicRequest request, MultipartFile file, long topicId, long userId) {
        final Topic topic = getTopic(topicId);


        if (!Objects.equals(topic.getCreator().getId(), userId)) {
            return;
        }

        if (Objects.nonNull(request)) {
            topic.setContent(request.getContent());
            topic.setEdited(true);
        }

        if (Objects.nonNull(file)) {
            Image image = imageService.addImage(file);
            topic.setImage(image);
            topic.setEdited(true);
        }

        topicRepository.save(topic);
    }

    public void deleteTopic(long topicId, long userId) {
        final Topic topic = getTopic(topicId);

        if (Objects.equals(topic.getCreator().getId(), userId)) {
            topicRepository.delete(topic);
        }
    }

    public List<GetTopicResponse> getTopics() {
        return topicRepository.findAllByOrderByCreateDateDesc().stream()
                .map(this::mapTopicToResponse)
                .collect(Collectors.toList());
    }

    protected Topic getTopic(long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new NullPointerException("Topic not found"));
    }

    private GetTopicResponse mapTopicToResponse(Topic topic) {
        final User user = topic.getCreator();

        GetTopicResponse response = new GetTopicResponse();
        response.setTopicId(topic.getId());
        response.setContent(topic.getContent());
        response.setImage(ImageUtil.getImageContent(topic.getImage()));
        response.setEdited(topic.getEdited());
        response.setCreateDate(topic.getCreateDate());
        response.setPhoto(ImageUtil.getImageContent(user.getPhoto()));
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        return response;
    }

}
