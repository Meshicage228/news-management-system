package ru.clevertec.userservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.userservice.dto.CreateUserDto;
import ru.clevertec.userservice.dto.UserResponseDto;
import ru.clevertec.userservice.service.UserService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponseDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.register(createUserDto);
    }

    @GetMapping("/login")
    public UserResponseDto getUser(@RequestParam(value = "username") @NotBlank(message = "Provide login") String username,
                                   @RequestParam(value = "password") @NotBlank(message = "Provide password") String password) {
        return userService.login(username, password);
    }
}
