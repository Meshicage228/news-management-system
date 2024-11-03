package ru.clevertec.cacheservice.property;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс, представляющий свойства конфигурации для LFU кэша.
 *
 * <p>Этот класс используется для настройки параметров LFU кэша в приложении. Свойства
 * считываются из конфигурации с префиксом {@code spring.cache.lfu.max-entries}.</p>
 *
 * <p>Класс активируется только в том случае, если активен профиль {@code lfu}.</p>
 */
@Data
@ConfigurationProperties(prefix = "spring.cache.lfu.max-entries")
@ConditionalOnProperty(prefix = "spring.profiles.active", havingValue = "lfu")
public class LFUCacheProperties {

    /**
     * Максимальное количество записей в LFU кэше.
     *
     * <p>По умолчанию установлено значение {@code 256}.</p>
     */
    private int size = 256;
}
