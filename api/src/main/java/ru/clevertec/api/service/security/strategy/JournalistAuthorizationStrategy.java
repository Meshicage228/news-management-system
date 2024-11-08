package ru.clevertec.api.service.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.service.impl.cache.CacheNewsService;
import ru.clevertec.api.service.security.RoleAuthorizationStrategy;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JournalistAuthorizationStrategy implements RoleAuthorizationStrategy {
    private final CacheNewsService newsService;

    @Override
    public boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context) {
        long newsId = getNewsId(context);
        NewsEntity news = newsService.getNewsById(newsId);
        return news.getAuthorName().equals(authentication.getName());
    }

    private long getNewsId(RequestAuthorizationContext context) {
        return Optional.ofNullable(context.getVariables().get("newsId"))
                .map(Long::parseLong)
                .orElseThrow(() -> new NewsNotFoundException(Long.parseLong(context.getVariables().get("newsId"))));
    }
}
