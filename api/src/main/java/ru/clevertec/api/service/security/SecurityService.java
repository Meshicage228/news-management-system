package ru.clevertec.api.service.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

/**
 * Интерфейс с решением по авторизации.
 *
 * <p>Этот интерфейс определяет метод для получения решения по авторизации на основе
 * предоставленного объекта и контекста авторизации.</p>
 */
public interface SecurityService {

    /**
     * Получение решения по авторизации.
     *
     * @param authenticationSupplier Поставщик, который предоставляет объект
     *                               {@link Authentication} для проверки данных.
     * @param context Контекст, содержащий информацию о текущем запросе.
     * @return {@link AuthorizationDecision} Результат решения по авторизации
     */
    AuthorizationDecision getDecision(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context);
}
