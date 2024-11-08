package ru.clevertec.api.service.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.api.service.security.RoleAuthorizationStrategy;

/**
 * Стратегия авторизации для администраторов, реализующая интерфейс {@link RoleAuthorizationStrategy}.
 */
@Component
@RequiredArgsConstructor
public class AdminAuthorizationStrategy implements RoleAuthorizationStrategy {

    /**
     * Проверяет, разрешена ли авторизация для данного аутентифицированного пользователя
     * в контексте указанного запроса.
     *
     * @param authentication Объект {@link Authentication}, представляющий аутентифицированного пользователя.
     * @param context Контекст авторизации, содержащий информацию о текущем запросе.
     * @return {@code true}, так как администраторы имеют доступ ко всем ресурсам.
     */
    @Override
    public boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context) {
        return true;
    }
}
