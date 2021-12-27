package com.edorm.services.images;

import com.edorm.entities.images.Image;

import java.util.Objects;

public final class ImageUtil {


    public static byte[] getImageContent(Image image) {
        return Objects.nonNull(image) ? image.getContent() : null;
    }

}
