package ru.clevertec.cacheservice.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.cachestarter.cache.LRUCache;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Lfu cache tests")
class LRUCacheTest {

    private LRUCache cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache(2, "TestCache");
    }

    @Test
    @DisplayName("Should put and get value from cache")
    void testPutAndGet() {
        cache.put("key1", "value1");
        assertEquals("value1", cache.get("key1", (Callable<Object>) null));
    }

    @Test
    @DisplayName("Should evict value from cache")
    void testEvict() {
        cache.put("key1", "value1");
        cache.evict("key1");
        assertNull(cache.get("key1"));
    }

    @Test
    @DisplayName("Should clear all values from cache")
    void testClear() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.clear();
        assertNull(cache.get("key1"));
        assertNull(cache.get("key2"));
    }

    @Test
    @DisplayName("Should maintain LRU behavior")
    void testLRUBehavior() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        assertNull(cache.get("key1"));
        assertEquals("value2", cache.get("key2", (Callable<Object>) null));
        assertEquals("value3", cache.get("key3", (Callable<Object>) null));
    }

    @Test
    @DisplayName("Should get value using loader when not present")
    void testGetWithLoader() {
        String result = cache.get("key1", () -> "loadedValue");
        assertEquals("loadedValue", result);
        assertEquals("loadedValue", cache.get("key1", (Callable<Object>) null));
    }

    @Test
    @DisplayName("Should allow null value when enabled")
    void testPutNullValueAllowed() {
        cache = new LRUCache(true, "TestCache", 2);
        cache.put("key1", null);
        assertNull(cache.get("key1"));
    }
}