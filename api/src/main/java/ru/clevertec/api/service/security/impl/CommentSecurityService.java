package ru.clevertec.api.service.security.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.api.service.security.AbstractSecurityService;
import ru.clevertec.api.service.security.strategy.AdminAuthorizationStrategy;
import ru.clevertec.api.service.security.strategy.SubscriberAuthorizationStrategy;

import java.util.Map;

/**
 * Сервис для управления авторизацией комментариев, наследующийся от {@link AbstractSecurityService}.
 */
@Component
public class CommentSecurityService extends AbstractSecurityService {

    /**
     * Конструктор со стратегиями авторизации для различных ролей.
     *
     * <p>Создает сопоставление ролей с соответствующими стратегиями авторизации:</p>
     * <ul>
     *     <li>{@code "ROLE_ADMIN"} - {@link AdminAuthorizationStrategy}</li>
     *     <li>{@code "ROLE_SUBSCRIBER"} - {@link SubscriberAuthorizationStrategy}</li>
     * </ul>
     *
     * @param subscriberAuthorizationStrategy Стратегия авторизации для подписчиков.
     * @param adminAuthorizationStrategy Стратегия авторизации для администраторов.
     */
    public CommentSecurityService(SubscriberAuthorizationStrategy subscriberAuthorizationStrategy, AdminAuthorizationStrategy adminAuthorizationStrategy) {
        roleAuthorizationStrategies = Map.of(
                "ROLE_ADMIN", adminAuthorizationStrategy,
                "ROLE_SUBSCRIBER", subscriberAuthorizationStrategy
        );
    }
}
