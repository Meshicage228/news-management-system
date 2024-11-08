package ru.clevertec.globalexceptionhandlingstarter.configuration;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.globalexceptionhandlingstarter.clients.FeignExceptionDecoder;
import ru.clevertec.globalexceptionhandlingstarter.handler.AppExceptionHandler;

/**
 * Конфигурация для обработки глобальных исключений в приложении.
 */
@Configuration
public class ExceptionHandlerConfig {
    /**
    * Декодер, обрабатывающий Feign исключения с клиентской части
    * */
    @Bean
    public ErrorDecoder feignExceptionDecoder(){
        return new FeignExceptionDecoder();
    }

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
