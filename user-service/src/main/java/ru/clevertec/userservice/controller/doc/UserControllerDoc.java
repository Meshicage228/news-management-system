package ru.clevertec.userservice.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.globalexceptionhandlingstarter.dto.ExceptionResponse;
import ru.clevertec.userservice.dto.CreateUserDto;
import ru.clevertec.userservice.dto.UserResponseDto;

public interface UserControllerDoc {
    @Operation(summary = "Создать нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания пользователя",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponseDto createUser (
            @Parameter(description = "Данные для создания пользователя", required = true)
            @RequestBody @Valid CreateUserDto createUserDto
    );

    @Operation(summary = "Получить пользователя по логину и паролю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "пользователь успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для получения пользователя",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/login")
    UserResponseDto getUser (
            @Parameter(description = "Имя пользователя", required = true, example = "user123")
            @RequestParam(value = "username") @NotBlank(message = "Provide login") String username,

            @Parameter(description = "Пароль пользователя", required = true, example = "password123")
            @RequestParam(value = "password") @NotBlank(message = "Provide password") String password
    );
}
