package com.edorm.models.messages;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMessageRequest {

    private Long userId;

    private String content;

    private MultipartFile image;

}
