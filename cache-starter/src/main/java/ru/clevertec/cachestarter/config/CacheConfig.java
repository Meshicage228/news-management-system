package ru.clevertec.cachestarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.clevertec.cachestarter.manager.LFUCacheManager;
import ru.clevertec.cachestarter.manager.LRUCacheManager;
import ru.clevertec.cachestarter.property.CustomCacheProperties;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Конфигурация кэша для управления стратегиями кэширования.
 *
 * <p>Этот класс содержит определения бинов для менеджеров кэша. <p>
 */
@AutoConfiguration(before = CacheAutoConfiguration.class)
@EnableConfigurationProperties(value = {CustomCacheProperties.class, CacheProperties.class})
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class CacheConfig {

    /**
     * Создает и настраивает менеджер кэша LRU.
     *
     * <p>Этот метод будет вызван только при активном профиле {@code lru}.</p>
     *
     * @param cacheProperties Общие свойства кэша.
     * @param lruCacheProperty Свойства для LRU кэша.
     * @return Бин {@link LRUCacheManager}.
     */
    @Bean
    @ConditionalOnProperty(prefix = "app.cache", name = "type", havingValue = "LRU")
    public LRUCacheManager lruCacheManager(CacheProperties cacheProperties,
                                           CustomCacheProperties lruCacheProperty) {
        return new LRUCacheManager(lruCacheProperty, cacheProperties);
    }

    /**
     * Создает и настраивает менеджер кэша LFU.
     *
     * <p>Этот метод будет вызван только при активном профиле {@code lfu}.</p>
     *
     * @param cacheProperties Общие свойства кэша.
     * @param lfuCacheProperty Свойства для LFU кэша.
     * @return Бин {@link LFUCacheManager}.
     */
    @Bean
    @ConditionalOnProperty(prefix = "app.cache", name = "type", havingValue = "LFU")
    public LFUCacheManager lfuCacheManager(CacheProperties cacheProperties,
                                           CustomCacheProperties lfuCacheProperty) {
        LFUCacheManager lfuCacheManager = new LFUCacheManager(lfuCacheProperty.getSize());
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            lfuCacheManager.setCacheNames(cacheNames);
        }
        return lfuCacheManager;
    }

    @Bean
    @ConditionalOnMissingBean(value = {LFUCacheManager.class, LRUCacheManager.class, CacheManager.class})
    @ConditionalOnProperty(prefix = "app.cache", name = "type", havingValue = "REDIS")
    public RedisCacheConfiguration defaultCacheConfig(CacheProperties properties, ObjectMapper objectMapper) {
        CacheProperties.Redis redisProperties = properties.getRedis();
        ObjectMapper redisObjectMapper = objectMapper.copy()
                .activateDefaultTyping(
                        objectMapper.getPolymorphicTypeValidator(),
                        ObjectMapper.DefaultTyping.EVERYTHING
                );
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Optional.ofNullable(redisProperties.getTimeToLive()).orElse(Duration.ZERO))
                .prefixCacheNameWith(Optional.ofNullable(redisProperties.getKeyPrefix()).orElse(""))
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper)));
    }
}
