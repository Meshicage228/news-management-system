package ru.clevertec.api.service.security.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.service.impl.cache.CacheCommentService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("subscriber strategy tests")
class SubscriberAuthorizationStrategyTest {
    @Mock
    private CacheCommentService commentService;

    @Mock
    private Authentication authentication;

    @Mock
    private CommentEntity commentEntity;

    @Mock
    private RequestAuthorizationContext context;

    @InjectMocks
    private SubscriberAuthorizationStrategy subscriberAuthorizationStrategy;

    private long newsId;
    private long commentId;
    private String authorName;
    private String differentAuthorName;
    private Map<String, String> variables;

    @BeforeEach
    public void setUp() {
        newsId = 1L;
        commentId = 2L;
        authorName = "author1";
        differentAuthorName = "author2";

        variables = new HashMap<>();
        variables.put("newsId", String.valueOf(newsId));
        variables.put("commentId", String.valueOf(commentId));
    }

    @Test
    @DisplayName("User success authorization")
    public void userIsAuthorized() {
        // Given
        when(context.getVariables()).thenReturn(variables);
        when(authentication.getName()).thenReturn(authorName);
        when(commentEntity.getAuthorName()).thenReturn(authorName);
        when(commentService.getComment(anyLong())).thenReturn(commentEntity);

        // When
        boolean result = subscriberAuthorizationStrategy.isAuthorized(authentication, context);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("User authorization failed due to different names")
    public void userNotAuthorized() {
        // Given
        when(context.getVariables()).thenReturn(variables);
        when(authentication.getName()).thenReturn(differentAuthorName);
        when(commentEntity.getAuthorName()).thenReturn(authorName);
        when(commentService.getComment(anyLong())).thenReturn(commentEntity);

        // When
        boolean result = subscriberAuthorizationStrategy.isAuthorized(authentication, context);

        // Then
        assertFalse(result);
    }
}