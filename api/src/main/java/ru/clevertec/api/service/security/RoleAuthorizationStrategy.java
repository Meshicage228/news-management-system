package ru.clevertec.api.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * Интерфейс стратегии авторизации ролей.
 *
 */
public interface RoleAuthorizationStrategy {

    /**
     * Проверяет, разрешена ли авторизация для данного аутентифицированного пользователя
     * в контексте указанного запроса.
     *
     * @param authentication Объект {@link Authentication}, представляющий аутентифицированного пользователя.
     * @param context Контекст авторизации, содержащий информацию о текущем запросе.
     * @return {@code true}, если авторизация разрешена; {@code false} в противном случае.
     */
    boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context);
}
