package com.edorm.services.users;

import com.edorm.entities.users.Resident;
import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.models.users.UserRegistrationCredentials;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ResidentService residentService;

    public void registerUser(UserRegistrationCredentials credentials) {
        User user = mapToUser(credentials);
        user = userService.addUser(user);

        Resident resident = mapToResident(credentials, user);
        residentService.addResident(resident);
    }

    private User mapToUser(UserRegistrationCredentials credentials) {
        User user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());
        user.setRole(Role.RESIDENT);
        return user;
    }

    private Resident mapToResident(UserRegistrationCredentials credentials, User user) {
        Resident resident = new Resident();
        resident.setFirstName(credentials.getFirstName());
        resident.setLastName(credentials.getLastName());
        resident.setBirthday(credentials.getBirthday());
        resident.setPhoneNumber(credentials.getPhoneNumber());
        resident.setUser(user);
        return resident;
    }

}
