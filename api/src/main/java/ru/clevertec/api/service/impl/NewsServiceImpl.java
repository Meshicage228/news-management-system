package ru.clevertec.api.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.mapper.NewsMapper;
import ru.clevertec.api.service.NewsService;
import ru.clevertec.api.service.impl.cache.CacheCommentService;
import ru.clevertec.api.service.impl.cache.CacheNewsService;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.util.Optional;

/**
 * Реализация сервиса для работы с новостями.
 * Обеспечивает создание, обновление, удаление новостей и получение новостей с комментариями.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final CacheNewsService cacheNewsService;
    private final CacheCommentService cacheCommentService;
    private final CommentMapper commentMapper;
    private final NewsMapper newsMapper;

    /**
     * Получает все короткие новости с постраничной навигацией.
     *
     * @param pageNo Номер страницы.
     * @param pageSize Размер страницы.
     * @param filter Фильтр для поиска новостей.
     * @return Страница коротких новостей.
     */
    @Override
    public Page<ShortNewsDto> getAllShortNews(Integer pageNo, Integer pageSize, NewsFilter filter) {
        log.info("Get all short news");
        return cacheNewsService.getPaginatedNews(pageNo, pageSize, filter)
                .map(newsMapper::toShortDto);
    }

    /**
     * Получает новость с комментариями.
     *
     * @param pageNo Номер страницы для комментариев.
     * @param pageSize Размер страницы для комментариев.
     * @param commentFilter Фильтр для поиска комментариев.
     * @return Объект, представляющий новость с комментариями.
     * @throws EntityNotFoundException Если новость с указанным идентификатором не найдена.
     */
    @Override
    public ExtendedNewsDto getNewsWithComments(Integer pageNo, Integer pageSize, CommentFilter commentFilter) {
        log.info("Get news with comments");
        NewsEntity newsById = cacheNewsService.getNewsById(commentFilter.getNewsId());

        Page<CreatedCommentDto> commentsPage = cacheCommentService.getPaginatedComments(commentFilter, pageNo, pageSize)
                .map(commentMapper::toDto);

        return Optional.ofNullable(newsById)
                .map(news -> {
                    ExtendedNewsDto extendedDto = newsMapper.toExtendedDto(news);
                    extendedDto.setComments(commentsPage);
                    return extendedDto;
                })
                .orElseThrow(() -> new NewsNotFoundException(commentFilter.getNewsId()));
    }

    /**
     * Создает новую новость.
     *
     * @param createNewsDto Объект с данными для создания новости.
     * @return Объект, представляющий созданную новость.
     */
    @Override
    public CreatedNewsDto createNews(CreateNewsDto createNewsDto) {
        log.info("Create new news based on: {}", createNewsDto);
        NewsEntity newsEntity = newsMapper.toEntity(createNewsDto);
        newsEntity = cacheNewsService.saveNews(newsEntity);
        return newsMapper.toDto(newsEntity);
    }

    /**
     * Полностью обновляет существующую новость.
     *
     * @param entityToUpdate Идентификатор новости, которую нужно обновить.
     * @param updateNewsDto Объект с данными для обновления новости.
     * @return Объект, представляющий обновленную новость.
     * @throws EntityNotFoundException Если новость с указанным идентификатором не найдена.
     */
    @Override
    public UpdatedNewsDto fullNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        log.info("Full news update for: {} based on: {}", entityToUpdate, updateNewsDto);

        NewsEntity newsById = cacheNewsService.getNewsById(entityToUpdate);
        NewsEntity updatedNews = cacheNewsService.fullUpdateNews(newsById, updateNewsDto);

        return newsMapper.toUpdatedDto(updatedNews);
    }

    /**
     * Частично обновляет существующую новость.
     *
     * @param entityToUpdate Идентификатор новости, которую нужно частично обновить.
     * @param updateNewsDto Объект с данными для частичного обновления новости.
     * @return Объект, представляющий обновленную новость.
     * @throws EntityNotFoundException Если новость с указанным идентификатором не найдена.
     */
    @Override
    public UpdatedNewsDto partNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        log.info("Part news update for: {} based on: {}", entityToUpdate, updateNewsDto);
        NewsEntity newsById = cacheNewsService.getNewsById(entityToUpdate);
        NewsEntity updatedNews = cacheNewsService.patchUpdateNews(newsById, updateNewsDto);

        return newsMapper.toUpdatedDto(updatedNews);
    }

    /**
     * Удаляет новость и связанные с ней комментарии.
     *
     * @param id Идентификатор новости, которую нужно удалить.
     */
    @Override
    public void deleteNews(Long id) {
        // todo : make cascade better
        log.info("Delete news: {}", id);
        cacheNewsService.getNewsById(id).getComments()
                .stream()
                .map(CommentEntity::getId)
                .forEach(cacheCommentService::deleteComment);
        cacheNewsService.deleteNews(id);
    }
}
