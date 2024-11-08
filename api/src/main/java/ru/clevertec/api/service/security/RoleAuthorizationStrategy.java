package ru.clevertec.api.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public interface RoleAuthorizationStrategy {
    boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context);
}
