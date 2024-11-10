package ru.clevertec.api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для запроса пользователя.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    /**
     * Логин пользователя.
     */
    @NotBlank(message = "Provide login")
    @Size(message = "Login should be from 3 symbols", min = 3)
    private String username;

    /**
     * Пароль пользователя.
     */
    @NotBlank(message = "Introduce password")
    @Size(message = "Password from 4 to 16 symbols", min = 4, max = 16)
    private String password;
}
