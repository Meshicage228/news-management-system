package ru.clevertec.api.service.impl;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.clevertec.api.clients.UserClient;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.dto.user.UserResponseDto;
import ru.clevertec.api.service.SecretKeyGenerator;
import ru.clevertec.api.service.TokenService;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserClient userClient;
    private final SecretKeyGenerator generator;
    private SecretKey secretKey;

    @Value(value = "${app.token.expire-time}")
    private long expiryTime;

    @PostConstruct
    public void init() {
        secretKey = generator.generateSecretKey();
    }

    @Override
    public String createToken(UserRequestDto userRequestDto) {
        UserResponseDto loginUser =
                userClient.getLoginUser(userRequestDto.getUsername(), userRequestDto.getPassword());

        return Jwts.builder().subject(loginUser.getUsername())
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .claim("username", loginUser.getUsername())
                .claim("id", loginUser.getId())
                .claim("role", loginUser.getRole())
                .signWith(secretKey)
                .compact();
    }
}
