package ru.clevertec.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.clevertec.userservice.annotation.RoleExists;
import ru.clevertec.userservice.enums.Role;

@Data
public class CreateUserDto {
    @NotBlank(message = "Предоставьте логин")
    @Size(message = "Логин должен быть от 3 символов", min = 3)
    private String username;
    @NotBlank(message = "Предоставьте пароль")
    @Size(message = "Размер пароля должен быть от 4 до 16 символов", min = 4, max = 16)
    private String password;
    @NotNull(message = "Предоставьте роль")
    @RoleExists(message = "Переданная роль не найдена")
    private Role role;
}
