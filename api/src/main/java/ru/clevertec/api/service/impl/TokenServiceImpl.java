package ru.clevertec.api.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.clevertec.api.clients.UserClient;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.dto.user.UserResponseDto;
import ru.clevertec.api.service.SecretKeyGenerator;
import ru.clevertec.api.service.TokenService;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
                .claim("role", loginUser.getRole())
                .claim("username", loginUser.getUsername())
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Authentication fromToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();

        Claims payload = (Claims) parser.parse(token).getPayload();

        String role = "ROLE_" + payload.get("role");
        String username = (String) payload.get("username");

        List<SimpleGrantedAuthority> list = Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(username, null, list);
    }
}
