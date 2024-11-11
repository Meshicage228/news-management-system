package ru.clevertec.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.clevertec.userservice.dto.CreateUserDto;
import ru.clevertec.userservice.dto.UserResponseDto;
import ru.clevertec.userservice.repository.RoleRepository;
import ru.clevertec.userservice.service.UserService;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.userservice.util.FileReaderUtil.readFile;

@WebMvcTest(controllers = UserController.class)
@DisplayName("Mock user controller test")
public class MockMvcUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    @DisplayName("create user successfully")
    void createUser() throws Exception {
        // Given
        UserResponseDto responseDto = objectMapper.readValue(readFile("/users/get_user_response.json"), UserResponseDto.class);
        String request = readFile("/users/save_user_request.json");

        Mockito.when(userService.register(Mockito.any(CreateUserDto.class)))
                .thenReturn(responseDto);

        //When / then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(responseDto.getUsername()))
                .andExpect(jsonPath("$.role").value(responseDto.getRole().toString()))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.password").value(responseDto.getPassword()));
    }

    @Test
    @DisplayName("create user bad request")
    void badRequestOnCreatingUser() throws Exception {
        // Given
        String request = readFile("/users/save_user_no_username.json");

        // When / then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));
    }

    @Test
    @DisplayName("get registered user")
    void getUser() throws Exception {
        // Given
        UserResponseDto responseDto = objectMapper.readValue(readFile("/users/get_user_response.json"), UserResponseDto.class);

        Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(responseDto);

        // When / then
        mockMvc.perform(get("/api/v1/users/login")
                        .param("username", "Vlad")
                        .param("password", "111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(responseDto.getUsername()))
                .andExpect(jsonPath("$.role").value(responseDto.getRole().toString()))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.password").value(responseDto.getPassword()));
    }
}
