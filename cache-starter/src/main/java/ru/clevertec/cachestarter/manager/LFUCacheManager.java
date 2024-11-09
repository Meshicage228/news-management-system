package ru.clevertec.cachestarter.manager;

import lombok.Getter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;
import ru.clevertec.cachestarter.cache.LFUCache;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Менеджер кэша, реализующий стратегию LFU.
 */
public class LFUCacheManager implements CacheManager {

    /**
     * Xранение кэшей.
     */
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    /**
     * Вместимость кэша.
     */
    private final int cacheCapacity;

    /**
     * Указывает, динамически ли создавать кэши.
     */
    private boolean dynamic = true;

    /**
     * Указывает, разрешены ли значения null в кэше.
     */
    @Getter
    private boolean allowNullValues = true;

    /**
     * Указывает, хранить ли значения по ссылке.
     */
    @Getter
    private boolean storeByValue = false;

    /**
     * Конструктор, создающий менеджер кэша с заданной вместимостью.
     *
     * @param cacheCapacity Вместимость кэша.
     */
    public LFUCacheManager(int cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    /**
     * Получает кэш по имени.
     *
     * <p>Если кэш не найден и {@code dynamic} равно {@code true},
     * создается новый кэш.</p>
     *
     * @param name Имя кэша.
     * @return Кэш с указанным именем или {@code null}, если кэш не найден и не создается.
     */
    @Override
    @Nullable
    public Cache getCache(String name) {
        Cache cache = this.cacheMap.get(name);
        if (cache == null && this.dynamic) {
            cache = this.cacheMap.computeIfAbsent(name, this::createLfuCache);
        }
        return cache;
    }

    /**
     * Получает имена всех кэшей.
     *
     * @return Непосредственно доступная коллекция имен кэшей.
     */
    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    /**
     * Устанавливает имена кэшей.
     *
     * <p>Если передана коллекция имен, создаются соответствующие кэши,
     * и {@code dynamic} устанавливается в {@code false}. Если коллекция равна {@code null},
     * {@code dynamic} устанавливается в {@code true}.</p>
     *
     * @param cacheNames Коллекция имен кэшей или {@code null}.
     */
    public void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(name, createLfuCache(name));
            }
            this.dynamic = false;
        } else {
            this.dynamic = true;
        }
    }

    /**
     * Создает новый LFU кэш с заданным именем.
     *
     * @param name Имя кэша.
     * @return Новый экземпляр {@link LFUCache} с указанными параметрами.
     */
    protected Cache createLfuCache(String name) {
        return new LFUCache(name, cacheCapacity, isAllowNullValues());
    }
}
