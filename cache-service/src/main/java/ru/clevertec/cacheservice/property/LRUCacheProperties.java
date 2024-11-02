package ru.clevertec.cacheservice.property;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.cache.lru.max-entries")
@ConditionalOnProperty(prefix = "spring.profiles.active", havingValue = "lru")
public class LRUCacheProperties {
    private int size = 256;
}
