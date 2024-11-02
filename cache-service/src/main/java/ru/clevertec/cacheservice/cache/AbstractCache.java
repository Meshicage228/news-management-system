package ru.clevertec.cacheservice.cache;

public abstract class AbstractCache {
    abstract void put(String key, Object value);

    abstract Object get(String key);
}
