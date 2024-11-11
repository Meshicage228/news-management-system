package ru.clevertec.api.service;

import org.springframework.security.core.Authentication;
import ru.clevertec.api.dto.user.UserRequestDto;

/**
 * Интерфейс для работы с токенами аутентификации.
 */
public interface TokenService {

    /**
     * Создает токен аутентификации для указанного пользователя.
     *
     * @param userRequestDto объект, содержащий информацию о пользователе, для которого необходимо создать токен
     * @return строка, токен аутентификации
     */
    String createToken(UserRequestDto userRequestDto);

    /**
     * Декодирует токен и возвращает объект аутентификации.
     *
     * @param token токен аутентификации, который необходимо декодировать
     * @return объект {@link Authentication}, содержащий информацию о пользователе, связанном с токеном
     */
    Authentication fromToken(String token);
}
