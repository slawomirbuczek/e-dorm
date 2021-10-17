package com.edorm.services.users;

import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.models.users.UserRegistrationCredentials;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final ModelMapper mapper;
    private final UserService userService;
    private final AddressService addressService;
    //private final MailService mailService;

    public void registerUser(UserRegistrationCredentials credentials) {
        User user = mapper.map(credentials, User.class);
        user.setRole(Role.RESIDENT);
        String password = generatePassword();
        user.setPassword(password);
        userService.addUser(user);
        /*Thread thread = new Thread(
                () -> mailService.sendCredentialsMail(user.getEmail(), user.getUsername(), password));
        thread.start();*/
    }


    private String generatePassword() {
        char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789".toCharArray();
        StringBuilder password = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 9; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }
        return password.toString();
    }

}
