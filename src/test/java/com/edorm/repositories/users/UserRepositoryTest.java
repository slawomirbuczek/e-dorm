package com.edorm.repositories.users;

import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private static final String USER_1_EMAIL = "test1@mail.com";
    private static final String USER_1_PASSWORD = "password1";
    private static final String USER_2_EMAIL = "test2@mail.com";
    private static final String USER_2_PASSWORD = "password2";

    private static final String INVALID_USER_EMAIL = "test0@mail.com";

    @Test
    void shouldFindAndReturnUserWhenUserExists() {
        getMockUsers().forEach(entityManager::persistFlushFind);

        Optional<User> optionalUser = userRepository.findByEmail(USER_1_EMAIL);

        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get().getId()).isNotNull();
        assertThat(optionalUser.get().getEmail()).isEqualTo(USER_1_EMAIL);
        assertThat(optionalUser.get().getPassword()).isEqualTo(USER_1_PASSWORD);
        assertThat(userRepository.existsByEmail(USER_1_EMAIL)).isTrue();

        assertThat(userRepository.findByEmail(USER_2_EMAIL).isPresent()).isTrue();
        assertThat(userRepository.existsByEmail(USER_2_EMAIL)).isTrue();

        assertThat(userRepository.findByEmail(INVALID_USER_EMAIL).isPresent()).isFalse();
        assertThat(userRepository.existsByEmail(INVALID_USER_EMAIL)).isFalse();
    }


    private List<User> getMockUsers() {
        User user1 = new User();
        user1.setEmail(USER_1_EMAIL);
        user1.setPassword(USER_1_PASSWORD);
        user1.setRole(Role.BASIC);
        user1.setPhoto(null);
        user1.setFirstName("firstname");
        user1.setLastName("lastname");

        User user2 = new User();
        user2.setEmail(USER_2_EMAIL);
        user2.setPassword(USER_2_PASSWORD);
        user2.setRole(Role.BASIC);
        user2.setPhoto(null);
        user2.setFirstName("firstname");
        user2.setLastName("lastname");

        return Arrays.asList(user1, user2);
    }

}