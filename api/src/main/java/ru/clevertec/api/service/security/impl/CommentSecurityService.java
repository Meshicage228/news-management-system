package ru.clevertec.api.service.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.api.service.security.AbstractSecurityService;
import ru.clevertec.api.service.security.strategy.AdminAuthorizationStrategy;
import ru.clevertec.api.service.security.strategy.SubscriberAuthorizationStrategy;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommentSecurityService extends AbstractSecurityService {
    public CommentSecurityService(SubscriberAuthorizationStrategy subscriberAuthorizationStrategy, AdminAuthorizationStrategy adminAuthorizationStrategy) {
        roleAuthorizationStrategies = Map.of(
                "ROLE_ADMIN", adminAuthorizationStrategy,
                "ROLE_SUBSCRIBER", subscriberAuthorizationStrategy
        );
    }
}
