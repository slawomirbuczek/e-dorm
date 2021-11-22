package com.edorm.models.users;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {

    private long id;

    private String firstName;

    private String lastName;

    private byte[] photo;

}
