package ru.clevertec.cacheservice.manager;

import lombok.Getter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;
import ru.clevertec.cacheservice.cache.LFUCache;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LFUCacheManager implements CacheManager {
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
    private final int cacheCapacity;
    private boolean dynamic = true;
    @Getter
    private boolean allowNullValues = true;
    @Getter
    private boolean storeByValue = false;

    public LFUCacheManager(int cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        Cache cache = this.cacheMap.get(name);
        if (cache == null && this.dynamic) {
            cache = this.cacheMap.computeIfAbsent(name, this::createLfuCache);
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

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

    protected Cache createLfuCache(String name) {
        return new LFUCache(name, cacheCapacity, isAllowNullValues());
    }
}
