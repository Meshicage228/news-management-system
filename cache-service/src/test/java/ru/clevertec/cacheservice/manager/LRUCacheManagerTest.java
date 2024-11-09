package ru.clevertec.cacheservice.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import ru.clevertec.cacheservice.cache.LRUCache;
import ru.clevertec.cacheservice.property.CustomCacheProperties;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Lru cache manager tests")
class LRUCacheManagerTest {
    @InjectMocks
    private LRUCacheManager lruCacheManager;

    @Mock
    private CustomCacheProperties lruCacheProperties;

    @Mock
    private CacheProperties cacheProperties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test creation of missing cache")
    void testGetMissingCache() {
        when(lruCacheProperties.getSize()).thenReturn(10);

        Cache cache = lruCacheManager.getMissingCache("testCache");

        assertNotNull(cache);
        assertEquals("testCache", cache.getName());
        assertInstanceOf(LRUCache.class, cache);
    }

    @Test
    @DisplayName("Test loading caches with no cache names")
    void testLoadCachesWithNoCacheNames() {
        when(cacheProperties.getCacheNames()).thenReturn(Collections.emptyList());

        Collection<? extends Cache> caches = lruCacheManager.loadCaches();
        assertTrue(caches.isEmpty());
    }
}