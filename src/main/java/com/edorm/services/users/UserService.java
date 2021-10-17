package com.edorm.services.users;

import com.edorm.dtos.users.UserDto;
import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.exceptions.users.EmailAlreadyTakenException;
import com.edorm.exceptions.users.TheSamePasswordException;
import com.edorm.exceptions.users.UserNotFoundException;
import com.edorm.exceptions.users.WrongPasswordException;
import com.edorm.models.users.RequestChangePassword;
import com.edorm.repositories.users.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    public UserDto getUserDto(long id) {
        return mapper.map(getUser(id), UserDto.class);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void changeRole(long id, Role role) {
        User user = getUser(id);
        user.setRole(role);
        userRepository.save(user);
    }

    public void addUser(User user) {
        if (emailAlreadyTaken(user.getEmail())) {
            throw new EmailAlreadyTakenException(user.getEmail());
        }

        String encodedPassword = encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void changePassword(Long userId, RequestChangePassword request) {
        if (request.getNewPassword().equals(request.getOldPassword())) {
            throw new TheSamePasswordException();
        }
        User user = getUser(userId);
        if (encoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(encodePassword(request.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new WrongPasswordException();
        }
    }


    private String encodePassword(String password) {
        return encoder.encode(password);
    }

    private boolean emailAlreadyTaken(String email) {
        return userRepository.existsByEmail(email);
    }

}
