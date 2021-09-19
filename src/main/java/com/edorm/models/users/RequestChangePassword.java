package com.edorm.models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestChangePassword {

    private String oldPassword;

    private String newPassword;

}