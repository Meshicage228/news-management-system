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
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.service.impl.cache.CacheNewsService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("journalist strategy tests")
class JournalistAuthorizationStrategyTest {
    @Mock
    private CacheNewsService newsService;

    @Mock
    private Authentication authentication;

    @Mock
    private RequestAuthorizationContext context;

    @Mock
    private NewsEntity newsEntity;

    @InjectMocks
    private JournalistAuthorizationStrategy journalistAuthorizationStrategy;

    private long newsId;
    private String authorName;
    private String differentAuthorName;
    private Map<String, String> variables;

    @BeforeEach
    public void setUp() {
        newsId = 1L;
        authorName = "author1";
        differentAuthorName = "author2";

        variables = new HashMap<>();
        variables.put("newsId", String.valueOf(newsId));
    }

    @Test
    @DisplayName("User success authorization")
    public void userIsAuthorized() {
        // Given
        when(context.getVariables()).thenReturn(variables);
        when(authentication.getName()).thenReturn(authorName);
        when(newsService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(newsEntity.getAuthorName()).thenReturn(authorName);

        // When
        boolean result = journalistAuthorizationStrategy.isAuthorized(authentication, context);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("User authorization failed due to different names")
    public void userNotAuthorized() {
        // Given
        when(context.getVariables()).thenReturn(variables);
        when(authentication.getName()).thenReturn(authorName);
        when(newsService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(newsEntity.getAuthorName()).thenReturn(differentAuthorName);

        // When
        boolean result = journalistAuthorizationStrategy.isAuthorized(authentication, context);

        // Then
        assertFalse(result);
    }
}