package com.edorm.controllers.users;

import com.edorm.auth.UserDetailsServiceImpl;
import com.edorm.config.JwtProperties;
import com.edorm.controllers.RestEndpoint;
import com.edorm.entities.users.User;
import com.edorm.enums.Role;
import com.edorm.models.users.UserCredentials;
import com.edorm.repositories.users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(value = LoginController.class)
class JWTLoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtProperties jwtProperties;

    @Mock
    private UserRepository userRepository;

    private static final String JWT_PREFIX = "Bearer ";

    private static UserCredentials userCredentials;

    private static final String USER_EMAIL = "test@mail.com";
    private static final String USER_PASSWORD = "password";

    @BeforeAll
    static void setUp() {
        userCredentials = new UserCredentials(USER_EMAIL, USER_PASSWORD);
    }

    @Test
    void shouldReturnJWTWhenCredentialsAreCorrect() throws Exception {
        given(userDetailsService.loadUserByUsername(USER_EMAIL)).willReturn(getMockUser());
        given(jwtProperties.getSecret()).willReturn(getMockKey());

        MvcResult result = mvc.perform(
                        post(RestEndpoint.LOGIN)
                                .content(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userCredentials)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).contains(JWT_PREFIX);
    }

    @Test
    void shouldReturnStatusUnauthorizedWhenCredentialsAreIncorrect() throws Exception {
        given(userDetailsService.loadUserByUsername(USER_EMAIL)).willThrow(UsernameNotFoundException.class);

        MvcResult result = mvc.perform(
                        post(RestEndpoint.LOGIN)
                                .content(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userCredentials)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


    private User getMockUser() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        user.setPassword(passwordEncoder.encode(USER_PASSWORD));
        user.setRole(Role.BASIC);
        return user;
    }

    private Key getMockKey() {
        return Keys.hmacShaKeyFor(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    }

}