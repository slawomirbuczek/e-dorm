package com.edorm.models.forum;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPostRequest {

    private String content;

    private MultipartFile image;

}
