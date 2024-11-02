package ru.clevertec.cacheservice.cache;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class LRUCache extends AbstractValueAdaptingCache {
    private final String name;
    private final int capacity;
    private Map<Object, Object> store;

    public LRUCache(String name, int capacity) {
        super(false);
        this.name = name;
        this.capacity = capacity;
    }

    public LRUCache(boolean allowNullValues, int capacity, String name) {
        super(allowNullValues);
        this.capacity = capacity;
        this.name = name;
    }

    public LRUCache(boolean allowNullValues, String name, int capacity) {
        super(allowNullValues);
        this.name = name;
        this.capacity = capacity;

        this.store = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                return size() > capacity;
            }
        };

    }

    @Override
    protected Object lookup(Object key) {
        return store.get(key);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return store;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = store.get(key);
        if (value == null) {
            try {
                value = valueLoader.call();
                put(key, value);
            } catch (Exception e) {
                throw new RuntimeException("ValueLoader failed", e);
            }
        }
        return (T) value;
    }

    @Override
    public void put(Object key, Object value) {
        if (value == null && !isAllowNullValues()) {
            throw new IllegalArgumentException("Null values are not allowed");
        }
        store.put(key, value);
    }

    @Override
    public void evict(Object key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }
}
