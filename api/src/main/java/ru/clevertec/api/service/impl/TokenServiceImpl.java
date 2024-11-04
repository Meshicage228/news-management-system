package ru.clevertec.api.service.impl;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.api.clients.UserClient;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.service.SecretKeyGenerator;
import ru.clevertec.api.service.TokenService;
import ru.clevertec.userservice.dto.UserResponseDto;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserClient userClient;
    private final SecretKeyGenerator generator;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = generator.generateSecretKey();
    }

    @Override
    public String createToken(UserRequestDto userRequestDto) {
        UserResponseDto loginUser =
                userClient.getLoginUser(userRequestDto.getUsername(), userRequestDto.getPassword());

        return Jwts.builder().subject(loginUser.getUsername())
                .claim("username", loginUser.getUsername())
                .claim("id", loginUser.getId())
                .claim("role", loginUser.getRole())
                .signWith(secretKey)
                .compact();
    }
}
