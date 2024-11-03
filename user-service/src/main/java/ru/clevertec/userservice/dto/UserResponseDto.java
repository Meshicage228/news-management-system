package ru.clevertec.userservice.dto;

import lombok.Data;
import ru.clevertec.userservice.enums.Role;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
