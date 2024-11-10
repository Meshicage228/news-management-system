package ru.clevertec.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для ответа пользователя.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    /**
     * Уникальный id пользователя.
     */
    private Long id;

    /**
     * Логин пользователя.
     */
    private String username;

    /**
     * Пароль пользователя (зашифрован).
     */
    private String password;

    /**
     * Роль пользователя.
     */
    private String role;
}
