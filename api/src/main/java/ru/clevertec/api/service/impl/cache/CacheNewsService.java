package ru.clevertec.api.service.impl.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.UpdateNewsDto;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.NewsMapper;
import ru.clevertec.api.repository.NewsRepository;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.util.Optional;
import static ru.clevertec.api.repository.specification.NewsSpecificationService.createNewsSpecification;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheNewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CacheManager cacheManager;

    @Cacheable(value = "newsCache", key = "#id")
    public NewsEntity getNewsById(long id) {
        log.info("Fetching news from database for id: {}", id);
        return newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
    }

    @CachePut(value = "newsCache", key = "#result.id")
    public NewsEntity saveNews(NewsEntity newsEntity) {
        log.info("Saving news to cache for id: {}", newsEntity.getId());
        return newsRepository.save(newsEntity);
    }

    @CachePut(value = "newsCache", key = "#result.id")
    public NewsEntity fullUpdateNews(NewsEntity newsEntity, UpdateNewsDto updateNewsDto) {
        log.info("Updating news to cache for id: {}", newsEntity.getId());
        return newsMapper.fullUpdate(newsEntity, updateNewsDto);
    }

    @CachePut(value = "newsCache", key = "#result.id")
    public NewsEntity patchUpdateNews(NewsEntity newsEntity, UpdateNewsDto updateNewsDto) {
        log.info("Part updating news to cache for id: {}", newsEntity.getId());
        return newsMapper.patchUpdate(newsEntity, updateNewsDto);
    }

    @CacheEvict(value = "newsCache", key = "#newsEntity.id")
    public void deleteNews(NewsEntity newsEntity) {
        log.info("Evicting news from cache for id: {}", newsEntity.getId());
        newsRepository.delete(newsEntity);
    }

    public Page<NewsEntity> getPaginatedNews(Integer pageNo, Integer pageSize, NewsFilter filter) {
        log.info("Fetching paginated news from database for page: {}, size: {}", pageNo, pageSize);
        Page<NewsEntity> all = newsRepository.findAll(createNewsSpecification(filter), PageRequest.of(pageNo, pageSize));
        putAllNewsIntoCache(all);
        return all;
    }

    public void putAllNewsIntoCache(Page<NewsEntity> newsPage) {
        Optional.ofNullable(newsPage)
                .ifPresent(newsEntities -> newsEntities.getContent()
                        .forEach(newsEntity -> cacheManager.getCache("newsCache")
                                .put(newsEntity.getId(), newsEntity)));
    }
}
