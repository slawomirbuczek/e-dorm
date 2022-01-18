package com.edorm.controllers.rentable;

import com.edorm.controllers.RestEndpoint;
import com.edorm.enums.rentable.RentableItemType;
import com.edorm.models.images.GetImageResponse;
import com.edorm.models.rentable.GetRentableItemResponse;
import com.edorm.services.rentable.RentableItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.RENTABLE_ITEM)
@AllArgsConstructor
public class RentableItemController {

    private final RentableItemService rentableItemService;

    @GetMapping("/{type}")
    public ResponseEntity<List<GetRentableItemResponse>> getRentableItems(@PathVariable RentableItemType type) {
        return ResponseEntity.ok(
                rentableItemService.getRentableItems(type)
        );
    }

    @GetMapping("/{rentableItemID}/image")
    public ResponseEntity<GetImageResponse> getRentableItemsImage(@PathVariable long rentableItemID) {
        return ResponseEntity.ok(
                rentableItemService.getRentableItemImage(rentableItemID)
        );
    }

    @PostMapping("/{type}")
    @ResponseStatus(HttpStatus.OK)
    public void addRentableItem(@RequestPart(required = false) String name, @RequestPart MultipartFile file, @PathVariable RentableItemType type) {
        rentableItemService.addRentableItem(name, file, type);
    }

}
