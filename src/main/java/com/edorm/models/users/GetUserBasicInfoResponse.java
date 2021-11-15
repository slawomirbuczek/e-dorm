package com.edorm.models.users;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserBasicInfoResponse {

    private String firstName;

    private byte[] photo;

}
