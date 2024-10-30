package ru.clevertec.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.core.dto.comment.*;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.exception.comment.CommentNotFoundException;
import ru.clevertec.core.exception.news.FailedToCreateNewsException;
import ru.clevertec.core.exception.news.NewsNotFoundException;
import ru.clevertec.core.mapper.CommentMapper;
import ru.clevertec.core.repository.CommentsRepository;
import ru.clevertec.core.repository.NewsRepository;
import ru.clevertec.core.service.CommentService;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final NewsRepository newsRepository;
    private final CommentsRepository commentsRepository;

    @Override
    @Transactional
    public void createComment(Long newsSource, CreateCommentDto createCommentDto) {
        log.info("Creating new comment for {}, on base of : {}", newsSource, createCommentDto);
        Optional.of(newsRepository.getReferenceById(newsSource))
                .ifPresentOrElse(newsEntity -> {
                    CommentEntity comment = commentMapper.toEntity(createCommentDto);
                    newsEntity.addComment(comment);
                }, FailedToCreateNewsException::new);
    }

    @Override
    @Transactional
    public UpdatedCommentDto partCommentUpdate(Long commentToUpdate, UpdateCommentDto updateCommentDto) {
        log.info("Updating comment for {}, on base of : {}", commentToUpdate, updateCommentDto);
        return Optional.of(commentsRepository.getReferenceById(commentToUpdate))
                .map(comment -> commentMapper.patchUpdate(comment, updateCommentDto))
                .map(commentMapper::toUpdatedComment)
                .orElseThrow(() -> new NewsNotFoundException(commentToUpdate));
    }

    @Override
    @Transactional
    public void deleteComment(Long newsSource, Long commentToDelete) {
        log.info("Deleting comment for news with id : {}, on base of :{}", newsSource, commentToDelete);
        NewsEntity newsEntity = newsRepository.findById(newsSource)
                .orElseThrow(() -> new NewsNotFoundException(newsSource));

        CommentEntity commentEntity = commentsRepository.findById(commentToDelete)
                .orElseThrow(() -> new CommentNotFoundException(commentToDelete));

        newsEntity.getComments().remove(commentEntity);
    }
}
