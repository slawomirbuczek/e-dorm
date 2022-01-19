package com.edorm.models.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetResidentInfoResponse {

    private String phoneNumber;

    private LocalDate birthday;

    private String firstName;

    private String lastName;

    private byte[] photo;

    private String roomNumber;

    private String compositionNumber;

}
