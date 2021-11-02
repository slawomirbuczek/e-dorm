package com.edorm.models.users;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
