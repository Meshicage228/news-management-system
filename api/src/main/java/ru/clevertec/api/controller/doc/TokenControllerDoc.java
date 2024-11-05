package ru.clevertec.api.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.clevertec.api.dto.user.UserRequestDto;

public interface TokenControllerDoc {

    @Operation(summary = "Создать токен для аутентификации пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Токен успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для аутентификации")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    String createToken(
            @Parameter(description = "Данные для аутентификации пользователя", required = true)
            @RequestBody @Valid UserRequestDto authenticationData
    );
}
