package com.edorm.services.rentable;

import com.edorm.entities.rentable.RentHistory;
import com.edorm.entities.rentable.RentableItem;
import com.edorm.repositories.rentable.RentHistoryRepository;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RentHistoryService {

    private final RentHistoryRepository rentHistoryRepository;
    private final RentableItemService rentableItemService;
    private final UserService userService;

    public void rentItem(long rentableItemId, long userId) {
        RentableItem rentableItem = rentableItemService.getRentableItem(rentableItemId);

        if (!rentableItem.getAvailable()) {
            throw new AccessDeniedException("Access denied");
        }

        rentableItemService.setAvailable(rentableItem, false);

        RentHistory rentHistory = new RentHistory();
        rentHistory.setRentableItem(rentableItem);
        rentHistory.setRentTime(LocalDateTime.now());
        rentHistory.setUser(userService.getUser(userId));
        rentHistoryRepository.save(rentHistory);
    }

    public void returnItem(long rentHistoryId, long userId) {
        RentHistory rentHistory = rentHistoryRepository.findById(rentHistoryId).orElseThrow(NullPointerException::new);

        if (rentHistory.getUser().getId() != userId) {
            throw new AccessDeniedException("Access denied");
        }

        rentHistory.setReturnTime(LocalDateTime.now());
        rentHistoryRepository.save(rentHistory);
        rentableItemService.setAvailable(rentHistory.getRentableItem(), true);
    }

}
