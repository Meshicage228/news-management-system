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

/**
 * Стратегия авторизации для журналистов, реализующая интерфейс {@link RoleAuthorizationStrategy}.
 */
@Component
@RequiredArgsConstructor
public class JournalistAuthorizationStrategy implements RoleAuthorizationStrategy {

    private final CacheNewsService newsService;
    /**
     * Проверяет, разрешена ли авторизация для данного аутентифицированного пользователя
     * в контексте указанного запроса.
     *
     * @param authentication Объект {@link Authentication}, представляющий аутентифицированного пользователя.
     * @param context Контекст авторизации, содержащий информацию о текущем запросе.
     * @return {@code true}, если авторизация разрешена (пользователь является автором новости);
     *         {@code false} в противном случае.
     */
    @Override
    public boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context) {
        long newsId = getNewsId(context);
        NewsEntity news = newsService.getNewsById(newsId);
        return news.getAuthorName().equals(authentication.getName());
    }

    /**
     * Извлекает Id новости из контекста авторизации запроса.
     *
     * @param context Контекст запроса.
     * @return Идентификатор новости.
     * @throws NewsNotFoundException Если Id новости не найден в контексте.
     */
    private long getNewsId(RequestAuthorizationContext context) {
        return Optional.ofNullable(context.getVariables().get("newsId"))
                .map(Long::parseLong)
                .orElseThrow(() -> new NewsNotFoundException(Long.parseLong(context.getVariables().get("newsId"))));
    }
}
