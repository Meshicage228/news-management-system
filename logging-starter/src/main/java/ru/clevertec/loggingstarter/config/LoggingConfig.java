package ru.clevertec.loggingstarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.loggingstarter.aspect.LogAspect;

/**
 * Конфигурационный класс для настройки аспектов логирования.
 *
 * <p>
 * Этот класс используется для создания экземпляров
 * аспектов, которые будут управлять логированием запросов и ответов
 * в приложении.
 * </p>
 */
@Configuration
public class LoggingConfig {

    /**
     * Создает бин для аспекта логирования.
     *
     * @return бин типа {@link LogAspect}
     */
    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
