package com.edorm.controllers.users;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.users.UserCredentials;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoint.LOGIN)
public class LoginController {

    @PostMapping
    public void login(@RequestBody UserCredentials credentials) {
    }

}
