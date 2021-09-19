package com.edorm.controllers.users;

import com.edorm.dtos.users.UserDto;
import com.edorm.models.users.RequestChangePassword;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserDto(Long.parseLong(principal.getName())));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDto(id));
    }

    @PutMapping("/user/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(Principal principal, @RequestBody RequestChangePassword request) {
        userService.changePassword(Long.parseLong(principal.getName()), request);
    }

}
