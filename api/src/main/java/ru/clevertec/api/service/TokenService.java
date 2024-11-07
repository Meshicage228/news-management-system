package ru.clevertec.api.service;

import org.springframework.security.core.Authentication;
import ru.clevertec.api.dto.user.UserRequestDto;

public interface TokenService {
    String createToken(UserRequestDto userRequestDto);

    Authentication fromToken(String token);
}
