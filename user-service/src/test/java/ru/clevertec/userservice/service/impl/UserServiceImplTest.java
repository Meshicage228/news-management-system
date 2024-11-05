package ru.clevertec.userservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.clevertec.globalexceptionhandlingstarter.exception.role.RoleNotFountException;
import ru.clevertec.globalexceptionhandlingstarter.exception.user.IncorrectCredentialsException;
import ru.clevertec.globalexceptionhandlingstarter.exception.user.UserNotFoundException;
import ru.clevertec.userservice.dto.CreateUserDto;
import ru.clevertec.userservice.dto.UserResponseDto;
import ru.clevertec.userservice.entity.RoleEntity;
import ru.clevertec.userservice.entity.UserEntity;
import ru.clevertec.userservice.enums.Role;
import ru.clevertec.userservice.mapper.UserMapper;
import ru.clevertec.userservice.repository.RoleRepository;
import ru.clevertec.userservice.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("User service tests")
class UserServiceImplTest {
    private UserEntity userEntity;
    private UserResponseDto userResponseDto;
    private CreateUserDto createUserDto;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        userEntity.setPassword("encodedPassword");

        userResponseDto = UserResponseDto.builder()
                .username(USERNAME)
                .build();

        createUserDto = CreateUserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .role(Role.SUBSCRIBER)
                .build();
    }

    @Test
    @DisplayName("get login result")
    void successLogin() {
        // When
        when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.of(userEntity));
        when(encoder.matches(eq(PASSWORD), any()))
                .thenReturn(true);
        when(userMapper.toDto(userEntity))
                .thenReturn(userResponseDto);

        UserResponseDto result = userService.login(USERNAME, PASSWORD);

        // Then
        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());

        verify(userRepository).findByUsername(USERNAME);
        verify(encoder).matches(PASSWORD, userEntity.getPassword());
        verify(userMapper).toDto(userEntity);
    }

    @Test
    @DisplayName("user not found")
    void userNotFound() {
        // When
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        // Then
        assertThrows(UserNotFoundException.class, () -> userService.login(USERNAME, PASSWORD));
        verify(userRepository).findByUsername(USERNAME);
    }

    @Test
    @DisplayName("incorrect password exception")
    void incorrectPassword() {
        // When
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        when(encoder.matches(eq("wrongPassword"), any())).thenReturn(false);

        // Then
        assertThrows(IncorrectCredentialsException.class,
                () -> userService.login(USERNAME, "wrongPassword"));
        verify(userRepository).findByUsername(USERNAME);
        verify(encoder).matches("wrongPassword", userEntity.getPassword());
    }

    @Test
    @DisplayName("successful registration")
    void registerSuccess() {
        // Given
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.SUBSCRIBER);
        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);

        // When
        when(roleRepository.findByRole(Role.SUBSCRIBER))
                .thenReturn(Optional.of(roleEntity));
        when(userMapper.toEntity(createUserDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userResponseDto);
        UserResponseDto result = userService.register(createUserDto);

        // Then
        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());

        verify(roleRepository).findByRole(Role.SUBSCRIBER);
        verify(userMapper).toEntity(createUserDto);
        verify(userRepository).save(userEntityCaptor.capture());
        verify(userMapper).toDto(userEntity);

        assertEquals(USERNAME, userEntityCaptor.getValue().getUsername());
    }

    @Test
    @DisplayName("role not found")
    void roleNotFound() {
        // Given
        when(roleRepository.findByRole(Role.SUBSCRIBER))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(RoleNotFountException.class, () -> userService.register(createUserDto));
        verify(roleRepository).findByRole(Role.SUBSCRIBER);
    }
}