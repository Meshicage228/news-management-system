package ru.clevertec.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.userservice.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @NotBlank(message = "Provide login")
    @Size(message = "Login should be from 3 symbols", min = 3)
    private String username;
    @NotBlank(message = "Introduce password")
    @Size(message = "Password from 4 to 16 symbols", min = 4, max = 16)
    private String password;
    @NotNull(message = "Provide role")
    private Role role;
}
