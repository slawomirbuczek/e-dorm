package com.edorm.services.announcements;

import com.edorm.entities.announcements.Announcement;
import com.edorm.entities.users.User;
import com.edorm.models.announcements.AddAnnouncementRequest;
import com.edorm.models.announcements.GetAnnouncementsResponse;
import com.edorm.repositories.announcements.AnnouncementRepository;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserService userService;

    public void addAnnouncement(AddAnnouncementRequest request, long userId) {
        User user = userService.getUser(userId);

        Announcement announcement = new Announcement();
        announcement.setSubject(request.getSubject());
        announcement.setContent(request.getContent());
        announcement.setDisabled(false);
        announcement.setDate(LocalDateTime.now());
        announcement.setCreator(user);
        announcementRepository.save(announcement);
    }

    @Transactional
    public List<GetAnnouncementsResponse> getAnnouncements() {
        return announcementRepository.findAllByDisabledFalseOrderByDateDesc().stream()
                .map(this::mapAnnouncementToResponse)
                .collect(Collectors.toList());
    }

    private GetAnnouncementsResponse mapAnnouncementToResponse(Announcement announcement) {
        User creator = announcement.getCreator();
        String fullName = creator.getFirstName() + " " + creator.getLastName();

        GetAnnouncementsResponse response = new GetAnnouncementsResponse();
        response.setFullName(fullName);
        response.setSubject(announcement.getSubject());
        response.setContent(announcement.getContent());
        response.setDate(announcement.getDate());
        return response;
    }

}
