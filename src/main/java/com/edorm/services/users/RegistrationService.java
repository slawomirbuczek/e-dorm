package com.edorm.services.users;

import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.models.users.UserRegistrationCredentials;
import com.edorm.services.mail.MailService;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final ModelMapper mapper;
    private final UserService userService;
    private final AddressService addressService;
    private final MailService mailService;

    public void registerUser(UserRegistrationCredentials credentials) {
        User user = mapper.map(credentials, User.class);
        user.setRole(Role.RESIDENT);
        user.setPassword(credentials.getPassword());
        userService.addUser(user);

        Executors.newSingleThreadExecutor().execute(
                () -> mailService.sendCredentialsMail(user.getEmail(), user.getUsername(), credentials.getPassword())
        );
    }

}
