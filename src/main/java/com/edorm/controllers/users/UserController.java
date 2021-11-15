package com.edorm.controllers.users;

import com.edorm.controllers.RestEndpoint;
import com.edorm.dtos.users.UserDto;
import com.edorm.models.users.RequestChangePassword;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping(RestEndpoint.USER)
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserDto(Long.parseLong(principal.getName())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDto(id));
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody RequestChangePassword request, Principal principal) {
        userService.changePassword(Long.parseLong(principal.getName()), request);
    }

    @PutMapping("/photo")
    @ResponseStatus(HttpStatus.OK)
    public void updatePhoto(@RequestPart MultipartFile photo, Principal principal) {
        userService.addPhoto(photo, Long.parseLong(principal.getName()));
    }

}
