package com.edorm.models.announcements;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAnnouncementRequest {

    private String subject;

    private String content;

}
