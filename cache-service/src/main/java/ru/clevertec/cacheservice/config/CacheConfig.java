package ru.clevertec.cacheservice.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.clevertec.cacheservice.manager.LFUCacheManager;
import ru.clevertec.cacheservice.manager.LRUCacheManager;
import ru.clevertec.cacheservice.property.LFUCacheProperties;
import ru.clevertec.cacheservice.property.LRUCacheProperties;

import java.util.List;

/**
 * Конфигурация кэша для управления стратегиями кэширования.
 *
 * <p>Этот класс содержит определения бинов для менеджеров кэша. <p>
 */
@AutoConfiguration(before = CacheAutoConfiguration.class)
@EnableConfigurationProperties(value = {LFUCacheProperties.class, LRUCacheProperties.class, CacheProperties.class})
public class CacheConfig {

    /**
     * Создает и настраивает менеджер кэша LRU.
     *
     * <p>Этот метод будет вызван только при активном профиле {@code lru}.</p>
     *
     * @param cacheProperties Общие свойства кэша.
     * @param lruCacheProperty Свойства для LRU кэша.
     * @return Бин {@link LRUCacheManager}.
     */
    @Bean
    @Profile("lru")
    public LRUCacheManager lruCacheManager(CacheProperties cacheProperties,
                                           LRUCacheProperties lruCacheProperty) {
        return new LRUCacheManager(lruCacheProperty, cacheProperties);
    }

    /**
     * Создает и настраивает менеджер кэша LFU.
     *
     * <p>Этот метод будет вызван только при активном профиле {@code lfu}.</p>
     *
     * @param cacheProperties Общие свойства кэша.
     * @param lfuCacheProperty Свойства для LFU кэша.
     * @return Бин {@link LFUCacheManager}.
     */
    @Bean
    @Profile("lfu")
    public LFUCacheManager lfuCacheManager(CacheProperties cacheProperties,
                                           LFUCacheProperties lfuCacheProperty) {
        LFUCacheManager lfuCacheManager = new LFUCacheManager(lfuCacheProperty.getSize());
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            lfuCacheManager.setCacheNames(cacheNames);
        }
        return lfuCacheManager;
    }
}
