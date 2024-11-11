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

/**
 * Реализация сервиса для работы с токенами аутентификации.
 * <p>
 * Этот класс отвечает за создание и декодирование токенов аутентификации.
 * Использует {@link UserClient} для получения информации о пользователе
 * и {@link SecretKeyGenerator} для генерации секретного ключа.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    /**
     * Клиент для взаимодействия с сервисом пользователей.
     * Используется для получения информации о пользователе при создании токена.
     */
    private final UserClient userClient;

    /**
     * Генератор секретного ключа.
     * Используется для создания секретного ключа, необходимого для подписи токенов.
     */
    private final SecretKeyGenerator generator;

    /**
     * Секретный ключ, используемый для подписи токенов аутентификации.
     */
    private SecretKey secretKey;

    /**
     * Время жизни токена в миллисекундах.
     * Получается из конфигурации приложения.
     */
    @Value(value = "${app.token.expire-time}")
    private long expiryTime;

    /**
     * Инициализация секрета при создании бина.
     */
    @PostConstruct
    public void init() {
        secretKey = generator.generateSecretKey();
    }

    /**
     * Создает токен аутентификации для указанного пользователя.
     *
     * @param userRequestDto объект, содержащий информацию о пользователе, для которого необходимо создать токен
     * @return строка, токен аутентификации
     */
    @Override
    public String createToken(UserRequestDto userRequestDto) {
        UserResponseDto loginUser  =
                userClient.getLoginUser(userRequestDto.getUsername(), userRequestDto.getPassword());

        return Jwts.builder()
                .subject(loginUser .getUsername())
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .claim("role", loginUser .getRole())
                .claim("username", loginUser .getUsername())
                .signWith(secretKey)
                .compact();
    }

    /**
     * Декодирует токен и возвращает объект аутентификации.
     *
     * @param token токен аутентификации, который необходимо декодировать
     * @return объект {@link Authentication}, содержащий информацию о пользователе, связанном с токеном
     */
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
