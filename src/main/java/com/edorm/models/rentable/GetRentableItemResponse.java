package com.edorm.models.rentable;

import com.edorm.enums.rentable.RentableItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRentableItemResponse {

    private long id;

    private String name;

    private boolean available;

    private RentableItemType type;

    private byte[] image;

}
