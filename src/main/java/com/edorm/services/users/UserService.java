package com.edorm.services.users;

import com.edorm.dtos.users.UserDto;
import com.edorm.entities.images.Image;
import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.exceptions.users.*;
import com.edorm.models.users.*;
import com.edorm.repositories.users.UserRepository;
import com.edorm.services.images.ImageService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;
    private final ImageService imageService;

    public UserDto getUserDto(long id) {
        return mapper.map(getUser(id), UserDto.class);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<GetUserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapUserToGetUserResponse).collect(Collectors.toList());
    }

    public List<User> getUsersWithIdNotInList(List<Long> usersId) {
        return userRepository.findAllByIdNotIn(usersId);
    }

    public GetUserBasicInfoResponse getUserBasicInfo(long userId) {
        User user = getUser(userId);
        byte[] photo = Objects.nonNull(user.getPhoto()) ? user.getPhoto().getContent() : null;

        GetUserBasicInfoResponse userBasicInfo = new GetUserBasicInfoResponse();
        userBasicInfo.setFirstName(user.getFirstName());
        userBasicInfo.setPhoto(photo);
        return userBasicInfo;
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

    public void addPhoto(MultipartFile photo, long userId) {
        Image image = imageService.addImage(photo);
        User user = getUser(userId);
        user.setPhoto(image);
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }

    private boolean emailAlreadyTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public GetUserResponse mapUserToGetUserResponse(User user) {
        byte[] photo = Objects.nonNull(user.getPhoto()) ? user.getPhoto().getContent() : null;

        GetUserResponse getUserResponse = new GetUserResponse();
        getUserResponse.setId(user.getId());
        getUserResponse.setFirstName(user.getFirstName());
        getUserResponse.setLastName(user.getLastName());
        getUserResponse.setPhoto(photo);
        return getUserResponse;
    }

}
