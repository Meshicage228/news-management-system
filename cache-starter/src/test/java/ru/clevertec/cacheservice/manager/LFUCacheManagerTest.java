package ru.clevertec.cacheservice.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import ru.clevertec.cachestarter.manager.LFUCacheManager;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Lfu cache manager tests")
class LFUCacheManagerTest {
    private LFUCacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = new LFUCacheManager(5);
    }

    @Test
    @DisplayName("Test dynamic creation of cache")
    void testGetCacheDynamicCreation() {
        Cache cache = cacheManager.getCache("testCache");
        assertNotNull(cache);
        assertEquals("testCache", cache.getName());
    }

    @Test
    @DisplayName("Test retrieval of existing cache")
    void testGetCacheExisting() {
        cacheManager.getCache("testCache");
        Cache cache = cacheManager.getCache("testCache");
        assertNotNull(cache);
        assertEquals("testCache", cache.getName());
    }

    @Test
    @DisplayName("Test return null for non-existent cache when dynamic creation is disabled")
    void testGetCacheNullIfNotDynamic() {
        cacheManager.setCacheNames(Arrays.asList("cache1", "cache2"));
        Cache cache = cacheManager.getCache("nonExistentCache");
        assertNull(cache);
    }
}