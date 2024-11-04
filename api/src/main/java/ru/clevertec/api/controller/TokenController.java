package ru.clevertec.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.service.TokenService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping
    @ResponseStatus(CREATED)
    public String createToken(@RequestBody UserRequestDto authenticationData) {
        return tokenService.createToken(authenticationData);
    }
}
