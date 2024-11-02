package ru.clevertec.cacheservice.cache;

import org.springframework.cache.Cache;

import java.util.*;
import java.util.concurrent.Callable;

public class LFUCache implements Cache {
    private final String name;
    private final int capacity;
    private final boolean allowNullValues;
    private final Map<Object, Object> cache;
    private final Map<Object, Integer> frequencyMap;
    private final Map<Integer, Set<Object>> frequencyList;
    private int minFrequency;

    public LFUCache(String name) {
        this(name, 256, false);
    }

    public LFUCache(String name, boolean allowNullValues) {
        this(name, 256, allowNullValues);
    }

    public LFUCache(String name, int capacity) {
        this(name, capacity, false);
    }

    public LFUCache(String name, int capacity, boolean allowNullValues) {
        this.name = name;
        this.capacity = capacity;
        this.allowNullValues = allowNullValues;
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
        this.frequencyList = new HashMap<>();
        this.minFrequency = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return cache;
    }

    @Override
    public ValueWrapper get(Object key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        updateFrequency(key);
        return () -> cache.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper valueWrapper = get(key);
        if (valueWrapper != null) {
            return type.cast(valueWrapper.get());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper valueWrapper = get(key);
        if (valueWrapper != null) {
            return (T) valueWrapper.get();
        }
        try {
            T value = valueLoader.call();
            put(key, value);
            return value;
        } catch (Exception e) {
            throw new RuntimeException("ValueLoader failed", e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        if (capacity <= 0)
            return;

        if (value == null && !allowNullValues) {
            throw new IllegalArgumentException("Null values are not allowed");
        }

        if (cache.containsKey(key)) {
            cache.put(key, value);
            updateFrequency(key);
            return;
        }

        if (cache.size() >= capacity) {
            evict(key);
        }

        cache.put(key, value);
        frequencyMap.put(key, 1);
        frequencyList.computeIfAbsent(1, k -> new HashSet<>()).add(key);
        minFrequency = 1;
    }

    @Override
    public void evict(Object key) {
        if (!cache.containsKey(key))
            return;

        int frequency = frequencyMap.get(key);
        frequencyMap.remove(key);
        cache.remove(key);
        frequencyList.get(frequency).remove(key);

        if (frequencyList.get(frequency).isEmpty()) {
            if (frequency == minFrequency) {
                minFrequency++;
            }
            frequencyList.remove(frequency);
        }
    }

    @Override
    public void clear() {
        cache.clear();
        frequencyMap.clear();
        frequencyList.clear();
        minFrequency = 0;
    }

    private void updateFrequency(Object key) {
        int frequency = frequencyMap.get(key);
        frequencyMap.put(key, frequency + 1);
        frequencyList.get(frequency).remove(key);

        if (frequencyList.get(frequency).isEmpty()) {
            if (frequency == minFrequency) {
                minFrequency++;
            }
            frequencyList.remove(frequency);
        }

        frequencyList.computeIfAbsent(frequency + 1, k -> new HashSet<>()).add(key);
    }
}