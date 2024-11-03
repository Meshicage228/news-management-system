package ru.clevertec.api.service.impl.cache;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.repository.CommentsRepository;
import ru.clevertec.globalexceptionhandlingstarter.exception.comment.CommentNotFoundException;

import static ru.clevertec.api.repository.specification.CommentSpecificationService.createCommentSpecification;

/**
 * Сервис для работы с комментариями, который управляет кэшированием комментариев.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheCommentService {
    private final CommentMapper commentMapper;
    private final CommentsRepository commentsRepository;

    /**
     * Сохраняет комментарий в кэше и базе данных.
     *
     * @param comment Комментарий, который нужно сохранить.
     * @return Сохраненный комментарий.
     */
    @CachePut(value = "comment", key = "#result.id")
    public CommentEntity saveComment(CommentEntity comment) {
        log.info("Saving comment to cache for id: {}", comment.getId());
        return commentsRepository.save(comment);
    }

    /**
     * Получает комментарий из кэша или базы данных.
     *
     * @param id Идентификатор комментария.
     * @return Комментарий, соответствующий указанному идентификатору.
     * @throws CommentNotFoundException Если комментарий с указанным идентификатором не найден.
     */
    @Cacheable(value = "comment", key = "#id")
    public CommentEntity getComment(Long id) {
        return commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    /**
     * Частично обновляет комментарий и сохраняет изменения в кэше и базе данных.
     *
     * @param comment Комментарий, который нужно обновить.
     * @param updateCommentDto Объект с данными для обновления комментария.
     * @return Обновленный комментарий.
     */
    @Transactional
    @CachePut(value = "comment", key = "#comment.id")
    public CommentEntity partCommentUpdate(CommentEntity comment, UpdateCommentDto updateCommentDto) {
        log.info("Updating comment for {}, on base of : {}", comment.getId(), updateCommentDto);
        return commentMapper.patchUpdate(comment, updateCommentDto);
    }

    /**
     * Удаляет комментарий из кэша и базы данных.
     *
     * @param commentId Идентификатор комментария, который нужно удалить.
     */
    @CacheEvict(value = "comment", key = "#commentId")
    public void deleteComment(Long commentId) {
        log.info("Deleting comment for {}", commentId);
        commentsRepository.deleteById(commentId);
    }

    /**
     * Получает страницу комментариев с учетом фильтрации.
     *
     * @param commentFilter Фильтр для поиска комментариев.
     * @param pageNo Номер страницы.
     * @param pageSize Размер страницы.
     * @return Страница комментариев, соответствующая заданным критериям.
     */
    public Page<CommentEntity> getPaginatedComments(CommentFilter commentFilter, Integer pageNo, Integer pageSize) {
        log.info("Fetching paginated comments from database for page: {}, size: {}, newsId: {}", pageNo, pageSize, commentFilter.getNewsId());
        return commentsRepository.findAll(createCommentSpecification(commentFilter), PageRequest.of(pageNo, pageSize));
    }
}
