package com.edorm.controllers.rentable;

import com.edorm.controllers.RestEndpoint;
import com.edorm.entities.rentable.RentableItem;
import com.edorm.services.rentable.RentableItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.RENTABLE_ITEM)
@AllArgsConstructor
public class RentableItemController {

    private final RentableItemService rentableItemService;

    @GetMapping
    public ResponseEntity<List<RentableItem>> getRentableItems() {
        return ResponseEntity.ok(rentableItemService.getRentableItems());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addRentableItem(@RequestBody RentableItem rentableItem) {
        rentableItemService.addRentableItem(rentableItem);
    }


}
