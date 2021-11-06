package com.edorm.controllers.users;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.users.UserRegistrationCredentials;
import com.edorm.services.users.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(RestEndpoint.REGISTRATION)
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@Valid @RequestBody UserRegistrationCredentials credentials) {
        registrationService.registerUser(credentials);
    }


}
