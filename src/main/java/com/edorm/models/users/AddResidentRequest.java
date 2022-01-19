package com.edorm.models.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AddResidentRequest {

    private String phoneNumber;

    private LocalDate birthday;

    private String roomNumber;

}
