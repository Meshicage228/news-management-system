package ru.clevertec.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import ru.clevertec.api.service.security.SecurityService;

/**
 * Конфигурация безопасности приложения.
 * <p>
 * Этот класс настраивает безопасность HTTP для приложения, определяя правила
 * доступа к различным конечным точкам API и добавляя фильтр декодирования токенов.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationSecurityConfig {

    private final TokenDecoderFilter tokenDecoderFilter;

    /**
     * Создает цепочку фильтров безопасности.
     *
     * @param http объект конфигурации безопасности для настройки
     * @param newsSecurityService сервис для проверки доступа к новостям
     * @param commentSecurityService сервис для проверки доступа к комментариям
     */
    @Bean
    public SecurityFilterChain chain(HttpSecurity http,
                                     SecurityService newsSecurityService,
                                     SecurityService commentSecurityService) throws Exception {

        return http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/tokens").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/news", "/api/v1/news/comments").permitAll()
                        .requestMatchers("/api/v1/news/{newsId}/comments/{commentId}").access(commentSecurityService::getDecision)
                        .requestMatchers("/api/v1/news/{newsId}").access(newsSecurityService::getDecision)
                        .requestMatchers(HttpMethod.POST,"/api/v1/news").hasAnyRole("ADMIN", "JOURNALIST")
                        .requestMatchers(HttpMethod.POST,"/api/v1/news/{newsId}/comments").hasAnyRole("ADMIN", "SUBSCRIBER")
                )
                .addFilterBefore(tokenDecoderFilter, SecurityContextHolderAwareRequestFilter.class)
                .build();
    }
}
