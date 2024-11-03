package ru.clevertec.globalexceptionhandlingstarter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.globalexceptionhandlingstarter.handler.AppExceptionHandler;

/**
 * Конфигурация для обработки глобальных исключений в приложении.
 */
@Configuration
public class ExceptionHandlerConfig {

    /**
     * Глобальный обработчик исключений для контроллеров REST.
     *
     * <p>Этот класс будет перехватывать исключения, выбрасываемые в контроллерах,
     * и обрабатывать их соответствующим образом.</p>
     */
    @RestControllerAdvice
    private class GlobalExceptionHandler extends AppExceptionHandler {

    }
}
