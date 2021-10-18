package com.edorm.services.rentable;

import com.edorm.entities.rentable.RentableItem;
import com.edorm.repositories.rentable.RentableItemRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RentableItemService {

    private final RentableItemRepository rentableItemRepository;

    public void addRentableItem(RentableItem rentableItem) {
        rentableItemRepository.save(rentableItem);
    }

    public List<RentableItem> getRentableItems() {
        return rentableItemRepository.findAll();
    }

    public RentableItem getRentableItem(long rentableItemId) {
        return rentableItemRepository.findById(rentableItemId)
                .orElseThrow(NullPointerException::new);
    }

    public void setAvailable(RentableItem rentableItem, boolean available) {
        rentableItem.setAvailable(available);
        rentableItemRepository.save(rentableItem);
    }

}
