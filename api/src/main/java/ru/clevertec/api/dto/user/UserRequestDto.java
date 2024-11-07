package ru.clevertec.api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Предоставьте логин")
    @Size(message = "Логин должен быть от 3 символов", min = 3)
    private String username;
    @NotBlank(message = "Предоставьте пароль")
    @Size(message = "Размер пароля должен быть от 4 до 16 символов", min = 4, max = 16)
    private String password;
}
