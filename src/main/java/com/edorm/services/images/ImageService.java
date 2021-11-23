package com.edorm.services.images;

import com.edorm.entities.images.Image;
import com.edorm.repositories.images.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image addImage(MultipartFile multipartFile) {

        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            return null;
        }

        Image image = new Image();
        image.setName(multipartFile.getName());

        try {
            image.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed");
        }

        return imageRepository.save(image);
    }

}
