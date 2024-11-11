package ru.clevertec.api.service.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.service.impl.cache.CacheCommentService;
import ru.clevertec.api.service.security.RoleAuthorizationStrategy;
import ru.clevertec.globalexceptionhandlingstarter.exception.comment.CommentNotFoundException;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.util.Optional;

/**
 * Стратегия авторизации для подписчиков, реализующая интерфейс {@link RoleAuthorizationStrategy}.
 */
@Component
@RequiredArgsConstructor
public class SubscriberAuthorizationStrategy implements RoleAuthorizationStrategy {

    private final CacheCommentService commentService;

    /**
     * Проверяет, разрешена ли авторизация для данного аутентифицированного пользователя
     * в контексте указанного запроса.
     *
     * @param authentication Объект {@link Authentication}, представляющий аутентифицированного пользователя.
     * @param context Контекст авторизации, содержащий информацию о текущем запросе.
     * @return {@code true}, если авторизация разрешена (пользователь является автором комментария);
     *         {@code false} в противном случае.
     */
    @Override
    public boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context) {
        long newsId = getNewsId(context);
        long commentId = getCommentId(context);
        CommentEntity comment = commentService.getComment(commentId);
        return comment.getAuthorName().equals(authentication.getName());
    }

    /**
     * Извлекает Id комментария из контекста авторизации запроса.
     *
     * @param context Контекст запроса.
     * @return Id комментария.
     * @throws CommentNotFoundException Если Id комментария не найден в контексте.
     */
    private long getCommentId(RequestAuthorizationContext context) {
        return Optional.ofNullable(context.getVariables().get("commentId"))
                .map(Long::parseLong)
                .orElseThrow(() -> new CommentNotFoundException(Long.parseLong(context.getVariables().get("commentId"))));
    }

    /**
     * Извлекает Id новости из контекста авторизации запроса.
     *
     * @param context Контекст запроса.
     * @return Id новости.
     * @throws NewsNotFoundException Если Id новости не найден в контексте.
     */
    private long getNewsId(RequestAuthorizationContext context) {
        return Optional.ofNullable(context.getVariables().get("newsId"))
                .map(Long::parseLong)
                .orElseThrow(() -> new NewsNotFoundException(Long.parseLong(context.getVariables().get("newsId"))));
    }
}
