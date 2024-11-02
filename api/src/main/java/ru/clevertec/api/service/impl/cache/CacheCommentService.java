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
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.repository.CommentsRepository;
import ru.clevertec.globalexceptionhandlingstarter.exception.comment.CommentNotFoundException;

import static ru.clevertec.api.repository.specification.CommentSpecificationService.createCommentSpecification;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheCommentService {
    private final CommentMapper commentMapper;
    private final CommentsRepository commentsRepository;

    @CachePut(value = "comment", key = "#result.id")
    public CommentEntity saveComment(CommentEntity comment) {
        log.info("Saving comment to cache for id: {}", comment.getId());
        CommentEntity save = commentsRepository.save(comment);
        return save;
    }

    @Cacheable(value = "comment", key = "#id")
    public CommentEntity getComment(Long id) {
        return commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional
    @CachePut(value = "comment", key = "#comment.id")
    public CommentEntity partCommentUpdate(CommentEntity comment, UpdateCommentDto updateCommentDto) {
        log.info("Updating comment for {}, on base of : {}", comment.getId(), updateCommentDto);
        return commentMapper.patchUpdate(comment, updateCommentDto);
    }

    @Transactional
    @CacheEvict(value = "comment", key = "#commentToDelete.id")
    public void deleteComment(NewsEntity newsSource, CommentEntity commentToDelete) {
        newsSource.getComments().remove(commentToDelete);
    }

    public Page<CommentEntity> getPaginatedComments(CommentFilter commentFilter, Integer pageNo, Integer pageSize) {
        log.info("Fetching paginated comments from database for page: {}, size: {}, newsId: {}", pageNo, pageSize, commentFilter.getNewsId());
        return commentsRepository.findAll(createCommentSpecification(commentFilter), PageRequest.of(pageNo, pageSize));
    }
}
