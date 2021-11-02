package com.edorm.models.users;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestChangePassword {

    private String oldPassword;

    private String newPassword;

}