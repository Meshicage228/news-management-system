package ru.clevertec.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.userservice.enums.Role;

/**
 * DTO для создания нового пользователя.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    /**
     * Имя пользователя.
     */
    @NotBlank(message = "Укажите логин")
    @Size(message = "Логин должен содержать от 3 символов", min = 3)
    private String username;

    /**
     * Пароль пользователя.
     */
    @NotBlank(message = "Укажите пароль")
    @Size(message = "Пароль должен содержать от 4 до 16 символов", min = 4, max = 16)
    private String password;

    /**
     * Роль, назначенная пользователю.
     */
    @NotNull(message = "Укажите роль")
    private Role role;
}
