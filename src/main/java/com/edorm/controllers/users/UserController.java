package com.edorm.controllers.users;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.users.*;
import com.edorm.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.USER)
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/self")
    public ResponseEntity<GetUserBasicInfoResponse> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserBasicInfo(Long.parseLong(principal.getName())));
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
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
