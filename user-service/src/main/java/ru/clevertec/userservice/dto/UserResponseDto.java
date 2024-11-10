package ru.clevertec.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.userservice.enums.Role;

/**
 * DTO для ответа с информацией о пользователе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    /**
     * Уникальный id пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя (зашифрован).
     */
    private String password;

    /**
     * Роль пользователя.
     */
    private Role role;
}
