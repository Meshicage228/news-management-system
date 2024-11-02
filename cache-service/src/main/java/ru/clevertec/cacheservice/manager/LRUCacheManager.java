package ru.clevertec.cacheservice.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import ru.clevertec.cacheservice.cache.LRUCache;
import ru.clevertec.cacheservice.property.LRUCacheProperties;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LRUCacheManager extends AbstractCacheManager {
    private final LRUCacheProperties lruCacheProperties;
    private final CacheProperties cacheProperties;

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return cacheProperties.getCacheNames()
                .stream()
                .map(this::getMissingCache)
                .collect(Collectors.toSet());
    }

    @Override
    protected Cache getMissingCache(String name) {
        return new LRUCache(name, lruCacheProperties.getSize());
    }
}
