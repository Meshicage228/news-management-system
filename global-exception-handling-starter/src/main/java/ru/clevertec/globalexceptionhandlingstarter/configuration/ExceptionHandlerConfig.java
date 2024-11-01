package ru.clevertec.globalexceptionhandlingstarter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.globalexceptionhandlingstarter.handler.AppExceptionHandler;

@Configuration
public class ExceptionHandlerConfig {
    @Bean
    public AppExceptionHandler appExceptionHandler() {
        return new AppExceptionHandler();
    }
}
