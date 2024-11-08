package ru.clevertec.userservice.controller;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.globalexceptionhandlingstarter.exception.user.IncorrectCredentialsException;
import ru.clevertec.userservice.UserServiceApplication;
import ru.clevertec.userservice.config.PostgresContainerConfig;
import ru.clevertec.userservice.entity.UserEntity;
import ru.clevertec.userservice.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.userservice.util.FileReaderUtil.readFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {UserServiceApplication.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "classpath:db/insert-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = PostgresContainerConfig.class)
@DisplayName("User controller integration tests")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("create user successfully")
    void createUser() throws Exception {
        // Given
        String request = readFile("/users/save_user_request.json");
        String expectedPassword = JsonPath.read(request, "$.password");
        long expectedId = 2;

        // When / Then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isCreated());
        Assertions.assertThat(userRepository.findAll()).hasSize(2);
        UserEntity createdUser = userRepository.findById(expectedId)
                .orElseThrow();
        Assertions.assertThat(passwordEncoder.matches(expectedPassword, createdUser.getPassword())).isTrue();
    }

    @Test
    @DisplayName("get registered user")
    void getUser() throws Exception {
        // Given
        String username = "Vlad";
        String password = "111111";

        // When / then
        mockMvc.perform(get("/api/v1/users/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Vlad"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.password").value("$2a$10$bubPP66FrlTdC8BXF0rcrehfqZr5X8PjTHS5M1pJQFBquIWp7lWua"));
    }

    @Test
    @DisplayName("get registered user")
    void getExceptionWithBadPassword() throws Exception {
        // Given
        String username = "Vlad";
        String password = "1234";

        // When / then
        mockMvc.perform(get("/api/v1/users/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(IncorrectCredentialsException.class, result.getResolvedException()));
    }
}