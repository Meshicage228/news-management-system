package ru.clevertec.api.service;

import ru.clevertec.api.dto.user.UserRequestDto;

public interface TokenService {
    String createToken(UserRequestDto userRequestDto);
}
