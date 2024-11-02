package ru.clevertec.cacheservice.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.serializer.support.SerializationDelegate;
import org.springframework.lang.Nullable;
import ru.clevertec.cacheservice.cache.LFUCache;
import ru.clevertec.cacheservice.property.LFUCacheProperties;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LFUCacheManager implements CacheManager, BeanClassLoaderAware {
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
    private final int cacheCapacity;

    private boolean dynamic = true;

    @Getter
    private boolean allowNullValues = true;

    @Getter
    private boolean storeByValue = false;

    @Nullable
    private SerializationDelegate serialization;

    public LFUCacheManager(int cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.serialization = new SerializationDelegate(classLoader);
        if (isStoreByValue()) {
            recreateCaches();
        }
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

    public void setAllowNullValues(boolean allowNullValues) {
        if (allowNullValues != this.allowNullValues) {
            this.allowNullValues = allowNullValues;
            // Need to recreate all Cache instances with the new null-value configuration...
            recreateCaches();
        }
    }

    public void setStoreByValue(boolean storeByValue) {
        if (storeByValue != this.storeByValue) {
            this.storeByValue = storeByValue;
            // Need to recreate all Cache instances with the new store-by-value configuration...
            recreateCaches();
        }
    }

    private void recreateCaches() {
        for (Map.Entry<String, Cache> entry : this.cacheMap.entrySet()) {
            entry.setValue(createLfuCache(entry.getKey()));
        }
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
