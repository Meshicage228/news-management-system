package ru.clevertec.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.controller.doc.TokenControllerDoc;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.service.TokenService;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController implements TokenControllerDoc {
    private final TokenService tokenService;

    @Override
    public String createToken(UserRequestDto authenticationData) {
        return tokenService.createToken(authenticationData);
    }
}
