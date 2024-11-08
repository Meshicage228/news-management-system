package ru.clevertec.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.clevertec.api.service.TokenService;

import java.io.IOException;

/**
 * Фильтр для декодирования токенов аутентификации.
 * <p>
 * Этот фильтр обрабатывает входящие HTTP-запросы и извлекает токен из заголовка
 * "Authorization". Если токен присутствует и начинается с "Bearer ", он декодируется
 * с помощью сервиса токенов {@link TokenService}. После декодирования аутентификация
 * устанавливается в контексте.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TokenDecoderFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    /**
     * Выполняет фильтрацию запроса, декодируя токен аутентификации.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            if (authorization.startsWith("Bearer ")) {
                Authentication authentication = tokenService.fromToken(authorization.substring(7));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
