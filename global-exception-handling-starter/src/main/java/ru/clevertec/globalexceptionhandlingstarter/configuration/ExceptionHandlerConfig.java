package ru.clevertec.globalexceptionhandlingstarter.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.globalexceptionhandlingstarter.handler.AppExceptionHandler;

@Configuration
public class ExceptionHandlerConfig {

    @RestControllerAdvice
    private class GlobalExceptionHandler extends AppExceptionHandler {
    }
}
