package ru.clevertec.api.service.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractSecurityService implements SecurityService {
    protected Map<String, RoleAuthorizationStrategy> roleAuthorizationStrategies;

    @Override
    public AuthorizationDecision getDecision(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        Authentication authentication = authenticationSupplier.get();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        boolean granted = roles.stream()
                .filter(roleAuthorizationStrategies::containsKey)
                .anyMatch(role -> roleAuthorizationStrategies.get(role).isAuthorized(authentication, context));
        return new AuthorizationDecision(granted);
    }
}
