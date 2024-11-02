package ru.clevertec.cacheservice.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.clevertec.cacheservice.manager.LFUCacheManager;
import ru.clevertec.cacheservice.manager.LRUCacheManager;
import ru.clevertec.cacheservice.property.LFUCacheProperties;
import ru.clevertec.cacheservice.property.LRUCacheProperties;

import java.util.List;

@EnableConfigurationProperties(value = {LFUCacheProperties.class, LRUCacheProperties.class, CacheProperties.class})
public class CacheConfig {

    @Bean
    @Profile("lru")
    public LRUCacheManager lruCacheManager(CacheProperties cacheProperties,
                                           LRUCacheProperties lruCacheProperty) {
        return new LRUCacheManager(lruCacheProperty, cacheProperties);
    }

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
