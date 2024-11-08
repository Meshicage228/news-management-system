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

@Component
@RequiredArgsConstructor
public class SubscriberAuthorizationStrategy implements RoleAuthorizationStrategy {
    private final CacheCommentService commentService;

    @Override
    public boolean isAuthorized(Authentication authentication, RequestAuthorizationContext context) {
        long newsId = getNewsId(context);
        long commentId = getCommentId(context);
        CommentEntity comment = commentService.getComment(commentId);
        return comment.getAuthorName().equals(authentication.getName());
    }

    private long getCommentId(RequestAuthorizationContext context) {
        return Optional.ofNullable(context.getVariables().get("commentId"))
                .map(Long::parseLong)
                .orElseThrow(() -> new CommentNotFoundException(Long.parseLong(context.getVariables().get("commentId"))));
    }

    private long getNewsId(RequestAuthorizationContext context) {
        return Optional.ofNullable(context.getVariables().get("newsId"))
                .map(Long::parseLong)
                .orElseThrow(() -> new NewsNotFoundException(Long.parseLong(context.getVariables().get("newsId"))));
    }
}
