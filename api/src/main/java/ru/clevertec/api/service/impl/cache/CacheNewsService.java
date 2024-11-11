package ru.clevertec.api.service.impl.cache;

import jakarta.transaction.Transactional;
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

/**
 * Сервис для работы с новостями, который управляет кэшированием новостей.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheNewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CacheManager cacheManager;

    /**
     * Получает новость по идентификатору из кэша или базы данных.
     *
     * @param id Идентификатор новости.
     * @return Новость, соответствующая указанному идентификатору.
     * @throws NewsNotFoundException Если новость с указанным идентификатором не найдена.
     */
    @Cacheable(value = "newsCache", key = "#id")
    public NewsEntity getNewsById(Long id) {
        log.info("Fetching news from database for id: {}", id);
        return newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
    }

    /**
     * Сохраняет новость в кэше и базе данных.
     *
     * @param newsEntity Новость, которую нужно сохранить.
     * @return Сохраненная новость.
     */
    @CachePut(value = "newsCache", key = "#result.id")
    public NewsEntity saveNews(NewsEntity newsEntity) {
        log.info("Saving news to cache for id: {}", newsEntity.getId());
        return newsRepository.save(newsEntity);
    }

    /**
     * Полностью обновляет новость и сохраняет изменения в кэше и базе данных.
     *
     * @param newsEntity Новость, которую нужно обновить.
     * @param updateNewsDto Объект с данными для обновления новости.
     * @return Обновленная новость.
     */
    @CachePut(value = "newsCache", key = "#result.id")
    @Transactional
    public NewsEntity fullUpdateNews(NewsEntity newsEntity, UpdateNewsDto updateNewsDto) {
        log.info("Updating news to cache for id: {}", newsEntity.getId());
        return newsMapper.fullUpdate(newsEntity, updateNewsDto);
    }

    /**
     * Частично обновляет новость и сохраняет изменения в кэше и базе данных.
     *
     * @param newsEntity Новость, которую нужно частично обновить.
     * @param updateNewsDto Объект с данными для частичного обновления новости.
     * @return Обновленная новость.
     */
    @CachePut(value = "newsCache", key = "#result.id")
    @Transactional
    public NewsEntity patchUpdateNews(NewsEntity newsEntity, UpdateNewsDto updateNewsDto) {
        log.info("Part updating news to cache for id: {}", newsEntity.getId());
        return newsMapper.patchUpdate(newsEntity, updateNewsDto);
    }

    /**
     * Удаляет новость из кэша и базы данных.
     *
     * @param id Идентификатор новости, которую нужно удалить.
     */
    @CacheEvict(value = "newsCache", key = "#id")
    public void deleteNews(Long id) {
        log.info("Evicting news from cache for id: {}", id);
        newsRepository.deleteById(id);
    }

    /**
     * Получает страницу новостей с учетом фильтрации.
     *
     * @param pageNo Номер страницы.
     * @param pageSize Размер страницы.
     * @param filter Фильтр для поиска новостей.
     * @return Страница новостей, соответствующая заданным критериям.
     */
    public Page<NewsEntity> getPaginatedNews(Integer pageNo, Integer pageSize, NewsFilter filter) {
        log.info("Fetching paginated news from database for page: {}, size: {}", pageNo, pageSize);
        Page<NewsEntity> all = newsRepository.findAll(createNewsSpecification(filter), PageRequest.of(pageNo, pageSize));
        putAllNewsIntoCache(all);
        return all;
    }

    /**
     * Сохраняет все новости из страницы в кэш.
     *
     * @param newsPage Страница новостей, которую нужно сохранить в кэш.
     */
    public void putAllNewsIntoCache(Page<NewsEntity> newsPage) {
        Optional.ofNullable(newsPage)
                .ifPresent(newsEntities -> newsEntities.getContent()
                        .forEach(newsEntity -> cacheManager.getCache("newsCache")
                                .put(newsEntity.getId(), newsEntity)));
    }
}
