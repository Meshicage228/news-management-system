package ru.clevertec.cacheservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс, представляющий свойства конфигурации для LRU/LFU кэша.
 *
 * <p>Этот класс используется для настройки параметров LRU/LFU кэша в приложении. Свойства
 * считываются из конфигурации с префиксом {@code app.cache}.</p>
 */
@Data
@ConfigurationProperties(prefix = "app.cache")
public class CustomCacheProperties {

    /**
     * Максимальное количество записей в LFU/LRU кэше.
     *
     * <p>По умолчанию установлено значение {@code 256}.</p>
     */
    private int size = 256;
}
