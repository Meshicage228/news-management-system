package ru.clevertec.userservice.service;

import ru.clevertec.userservice.dto.CreateUserDto;
import ru.clevertec.userservice.dto.UserResponseDto;

/**
 * Интерфейс сервиса пользователя, предоставляющий методы для управления пользователями.
 * <p>
 * Этот интерфейс включает в себя функциональность для входа в систему и регистрации пользователей.
 * </p>
 */
public interface UserService {

    /**
     * Метод для входа пользователя в систему.
     *
     * @param username имя пользователя, используемое для входа.
     * @param password пароль пользователя.
     * @return {@link UserResponseDto} объект, содержащий информацию о пользователе после успешного входа.
     */
    UserResponseDto login(String username, String password);

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param createUserDto объект, содержащий информацию о новом пользователе.
     * @return {@link UserResponseDto} объект, содержащий информацию о зарегистрированном пользователе.
     */
    UserResponseDto register(CreateUserDto createUserDto);
}
