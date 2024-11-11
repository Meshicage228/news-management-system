package ru.clevertec.globalexceptionhandlingstarter.configuration;

import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.globalexceptionhandlingstarter.clients.FeignExceptionDecoder;
import ru.clevertec.globalexceptionhandlingstarter.handler.AppExceptionHandler;

/**
 * Конфигурация для обработки глобальных исключений в приложении.
 */
@AutoConfiguration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
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
