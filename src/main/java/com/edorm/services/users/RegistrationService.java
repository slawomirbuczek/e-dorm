package com.edorm.services.users;

import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.models.users.UserRegistrationCredentials;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public void registerUser(UserRegistrationCredentials credentials) {
        User user = mapToUser(credentials);
        userService.addUser(user);

    }

    private User mapToUser(UserRegistrationCredentials credentials) {
        User user = new User();
        user.setFirstName(credentials.getFirstName());
        user.setLastName(credentials.getLastName());
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());
        user.setRole(Role.BASIC);
        return user;
    }

}
