package ru.clevertec.api.service.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.api.service.security.RoleAuthorizationStrategy;

@Component
@RequiredArgsConstructor
public class AdminAuthorizationStrategy implements RoleAuthorizationStrategy {
    /**
     * @param authentication
     * @param context
     * @return
     */
    @Override
    public boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context) {
        return true;
    }
}
