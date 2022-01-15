package com.edorm.services.rentable;

import com.edorm.entities.images.Image;
import com.edorm.entities.rentable.RentableItem;
import com.edorm.enums.rentable.RentableItemType;
import com.edorm.models.rentable.GetRentableItemResponse;
import com.edorm.repositories.rentable.RentableItemRepository;
import com.edorm.services.images.ImageService;
import com.edorm.services.images.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentableItemService {

    private final RentableItemRepository rentableItemRepository;
    private final ImageService imageService;

    public void addRentableItem(String name, MultipartFile file, RentableItemType type) {
        final Image image = imageService.addImage(file);

        RentableItem rentableItem = new RentableItem();
        rentableItem.setName(name);
        rentableItem.setAvailable(true);
        rentableItem.setRentableItemType(type);
        rentableItem.setImage(image);
        rentableItemRepository.save(rentableItem);
    }

    public List<GetRentableItemResponse> getRentableItems() {
        return rentableItemRepository.findAll().stream()
                .map(this::mapRentableItemToResponse)
                .collect(Collectors.toList());
    }

    protected RentableItem getRentableItem(long rentableItemId) {
        return rentableItemRepository.findById(rentableItemId)
                .orElseThrow(NullPointerException::new);
    }

    protected void setAvailable(RentableItem rentableItem, boolean available) {
        rentableItem.setAvailable(available);
        rentableItemRepository.save(rentableItem);
    }

    private GetRentableItemResponse mapRentableItemToResponse(RentableItem rentableItem) {
        GetRentableItemResponse response = new GetRentableItemResponse();
        response.setId(rentableItem.getId());
        response.setName(rentableItem.getName());
        response.setAvailable(rentableItem.getAvailable());
        response.setType(rentableItem.getRentableItemType());
        response.setImage(ImageUtil.getImageContent(rentableItem.getImage()));
        return response;
    }

}
