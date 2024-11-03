package ru.clevertec.cacheservice.property;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс, представляющий свойства конфигурации для LRU кэша.
 *
 * <p>Этот класс используется для настройки параметров LRU кэша в приложении. Свойства
 * считываются из конфигурации с префиксом {@code spring.cache.lru.max-entries}.</p>
 *
 * <p>Класс активируется только в том случае, если активен профиль {@code lru}.</p>
 */
@Data
@ConfigurationProperties(prefix = "spring.cache.lru.max-entries")
@ConditionalOnProperty(prefix = "spring.profiles.active", havingValue = "lru")
public class LRUCacheProperties {

    /**
     * Максимальное количество записей в LRU кэше.
     *
     * <p>По умолчанию установлено значение {@code 256}.</p>
     */
    private int size = 256;
}