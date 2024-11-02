package ru.clevertec.api.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.repository.CommentsRepository;
import ru.clevertec.api.repository.NewsRepository;
import ru.clevertec.api.service.CommentService;
import ru.clevertec.globalexceptionhandlingstarter.exception.comment.CommentNotFoundException;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.FailedToCreateNewsException;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

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
