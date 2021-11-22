package com.edorm.controllers.announcements;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.announcements.AddAnnouncementRequest;
import com.edorm.models.announcements.GetAnnouncementsResponse;
import com.edorm.services.announcements.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.ANNOUNCEMENT)
@AllArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<List<GetAnnouncementsResponse>> getAnnouncements() {
        return ResponseEntity.ok(announcementService.getAnnouncements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addAnnouncement(@RequestBody AddAnnouncementRequest request, Principal principal) {
        announcementService.addAnnouncement(request, Long.parseLong(principal.getName()));
    }


}
