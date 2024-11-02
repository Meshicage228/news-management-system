package ru.clevertec.api.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.service.CommentService;
import ru.clevertec.api.service.impl.cache.CacheCommentService;
import ru.clevertec.api.service.impl.cache.CacheNewsService;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CacheCommentService cacheCommentService;
    private final CacheNewsService cacheNewsService;
    private final CacheManager cacheManager;

    @Override
    public CreatedCommentDto createComment(Long newsSource, CreateCommentDto createCommentDto) {
        log.info("Creating new comment for {}, based on: {}", newsSource, createCommentDto);
        NewsEntity newsById = cacheNewsService.getNewsById(newsSource);
        CommentEntity entity = commentMapper.toEntity(createCommentDto);
        entity.setNewsEntity(newsById);
        CommentEntity commentEntity = cacheCommentService.saveComment(entity);
        return commentMapper.toDto(commentEntity);
    }

    @Override
    public UpdatedCommentDto partCommentUpdate(Long commentToUpdate, UpdateCommentDto updateCommentDto) {
        log.info("Updating comment for {}, based on: {}", commentToUpdate, updateCommentDto);

        return Optional.of(cacheCommentService.getComment(commentToUpdate))
                .map(comment -> cacheCommentService.partCommentUpdate(comment, updateCommentDto))
                .map(commentMapper::toUpdatedComment)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }

    @Override
    public void deleteComment(Long newsSource, Long commentToDelete) {
        log.info("Deleting comment for news with id: {}, based on: {}", newsSource, commentToDelete);
        NewsEntity newsById = cacheNewsService.getNewsById(newsSource);
        Optional.of(cacheCommentService.getComment(commentToDelete))
                .ifPresent(c -> cacheCommentService.deleteComment(newsById, c));
    }
}
