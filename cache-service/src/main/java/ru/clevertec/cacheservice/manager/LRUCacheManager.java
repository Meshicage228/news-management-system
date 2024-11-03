package ru.clevertec.cacheservice.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import ru.clevertec.cacheservice.cache.LRUCache;
import ru.clevertec.cacheservice.property.LRUCacheProperties;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Менеджер кэша, реализующий LRU.
 * Этот класс управляет кэшами, используя свойства, заданные в {@link LRUCacheProperties}.
 */
@RequiredArgsConstructor
public class LRUCacheManager extends AbstractCacheManager {

    /**
     * Свойства конфигурации для LRU кэша.
     */
    private final LRUCacheProperties lruCacheProperties;

    /**
     * Общие свойства кэша.
     */
    private final CacheProperties cacheProperties;

    /**
     * Загружает кэши, определенные в конфигурации.
     *
     * @return Коллекция кэшей, загруженных из конфигурации.
     */
    @Override
    protected Collection<? extends Cache> loadCaches() {
        return cacheProperties.getCacheNames()
                .stream()
                .map(this::getMissingCache)
                .collect(Collectors.toSet());
    }

    /**
     * Создает новый кэш, если он отсутствует.
     *
     * @param name Имя кэша, который нужно создать.
     * @return Новый экземпляр {@link LRUCache} с заданным именем и размером.
     */
    @Override
    protected Cache getMissingCache(String name) {
        return new LRUCache(true, name, lruCacheProperties.getSize());
    }
}
