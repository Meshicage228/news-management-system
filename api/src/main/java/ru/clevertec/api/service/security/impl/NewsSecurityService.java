package ru.clevertec.api.service.security.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.api.service.security.AbstractSecurityService;
import ru.clevertec.api.service.security.strategy.AdminAuthorizationStrategy;
import ru.clevertec.api.service.security.strategy.JournalistAuthorizationStrategy;

import java.util.Map;

/**
 * Сервис для управления авторизацией новостей, наследующийся от {@link AbstractSecurityService}.
 */
@Component
public class NewsSecurityService extends AbstractSecurityService {

    /**
     * Конструктор со стратегиями авторизации для различных ролей.
     *
     * <p>Создает сопоставление ролей с соответствующими стратегиями авторизации:</p>
     * <ul>
     *     <li>{@code "ROLE_ADMIN"} - {@link AdminAuthorizationStrategy}</li>
     *     <li>{@code "ROLE_JOURNALIST"} - {@link JournalistAuthorizationStrategy}</li>
     * </ul>
     *
     * @param journalistAuthorizationStrategy Стратегия авторизации для журналистов.
     * @param adminAuthorizationStrategy Стратегия авторизации для администраторов.
     */
    public NewsSecurityService(JournalistAuthorizationStrategy journalistAuthorizationStrategy, AdminAuthorizationStrategy adminAuthorizationStrategy) {
        roleAuthorizationStrategies = Map.of(
                "ROLE_ADMIN", adminAuthorizationStrategy,
                "ROLE_JOURNALIST", journalistAuthorizationStrategy
        );
    }
}
