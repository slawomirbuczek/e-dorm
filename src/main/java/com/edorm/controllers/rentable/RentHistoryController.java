package com.edorm.controllers.rentable;

import com.edorm.controllers.RestEndpoint;
import com.edorm.services.rentable.RentHistoryService;
import com.edorm.utils.PrincipalUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(RestEndpoint.RENT_HISTORY)
@AllArgsConstructor
public class RentHistoryController {

    private final RentHistoryService rentHistoryService;

    @PostMapping("/{rentableItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void rentItem(@PathVariable Long rentableItemId, Principal principal) {
        rentHistoryService.rentItem(rentableItemId, PrincipalUtil.getUserId(principal));
    }

    @PutMapping("/{rentHistoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void returnItem(@PathVariable Long rentHistoryId, Principal principal) {
        rentHistoryService.returnItem(rentHistoryId, PrincipalUtil.getUserId(principal));
    }

}
