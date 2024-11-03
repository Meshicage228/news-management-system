package ru.clevertec.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.globalexceptionhandlingstarter.exception.role.RoleNotFountException;
import ru.clevertec.globalexceptionhandlingstarter.exception.user.FailedToCreateUserException;
import ru.clevertec.globalexceptionhandlingstarter.exception.user.UserNotFoundException;
import ru.clevertec.userservice.dto.CreateUserDto;
import ru.clevertec.userservice.dto.UserResponseDto;
import ru.clevertec.userservice.entity.RoleEntity;
import ru.clevertec.userservice.entity.UserEntity;
import ru.clevertec.userservice.mapper.UserMapper;
import ru.clevertec.userservice.repository.RoleRepository;
import ru.clevertec.userservice.repository.UserRepository;
import ru.clevertec.userservice.service.UserService;

import java.util.Optional;

/**
 * Реализация сервиса пользователя, предоставляющая функциональность для управления пользователями.
 * <p>
 * Этот класс предоставляет методы для входа в систему
 * и регистрации пользователей, а также обрабатывает соответствующую бизнес-логику.
 * </p>
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Метод для входа пользователя в систему.
     *
     * @param username имя пользователя, используемое для входа.
     * @param password пароль пользователя.
     * @return {@link UserResponseDto} объект, содержащий информацию о пользователе после успешного входа.
     * @throws UserNotFoundException если пользователь с указанным именем не найден или пароль неверный.
     */
    @Override
    public UserResponseDto login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(userEntity -> encoder.matches(password, userEntity.getPassword()))
                .map(userMapper::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param createUserDto Dto объект, содержащий информацию о новом пользователе.
     * @return {@link UserResponseDto} объект для отображения успешной регистрации пользователя.
     * @throws RoleNotFountException если роль, указанная в {@code createUserDto}, не найдена.
     * @throws FailedToCreateUserException Exception если создание пользователя не удалось.
     */
    @Override
    public UserResponseDto register(CreateUserDto createUserDto) {
        RoleEntity roleEntity = roleRepository.findByRole(createUserDto.getRole())
                .orElseThrow(RoleNotFountException::new);

        UserEntity userEntity = userMapper.toEntity(createUserDto);
        userEntity.setRoleEntity(roleEntity);

        return Optional.of(userRepository.save(userEntity))
                .map(userMapper::toDto)
                .orElseThrow(FailedToCreateUserException::new);
    }
}
