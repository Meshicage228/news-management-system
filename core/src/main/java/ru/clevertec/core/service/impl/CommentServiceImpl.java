package ru.clevertec.core.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.core.dto.comment.CreateCommentDto;
import ru.clevertec.core.dto.comment.CreatedCommentDto;
import ru.clevertec.core.dto.comment.UpdateCommentDto;
import ru.clevertec.core.dto.comment.UpdatedCommentDto;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.exception.NewsNotFoundException;
import ru.clevertec.core.mapper.CommentMapper;
import ru.clevertec.core.repository.CommentsRepository;
import ru.clevertec.core.repository.NewsRepository;
import ru.clevertec.core.service.CommentService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final NewsRepository newsRepository;
    private final CommentsRepository commentsRepository;

    @Override
    public List<CreatedCommentDto> getAllNews(Integer pageNo, Integer pageSize) {
        return List.of();
    }

    @Override
    public CreatedCommentDto createComment(Long newsSource, CreateCommentDto createCommentDto) {
        return Optional.of(newsRepository.getReferenceById(newsSource))
                .map(newsEntity -> {
                    CommentEntity entity = commentMapper.toEntity(createCommentDto);
                    newsEntity.getComments().add(entity);
                }).orElseThrow(new NewsNotFoundException(newsSource));

    }

    @Override
    public UpdatedCommentDto partCommentUpdate(Long commentToUpdate, UpdateCommentDto updateCommentDto) {
        return Optional.of(commentsRepository.getReferenceById(commentToUpdate))
                .map((comment) -> commentMapper.patchUpdate(comment, updateCommentDto))
                .map(commentMapper::toUpdatedComment)
                .orElseThrow(() -> new NewsNotFoundException(commentToUpdate));
    }

    @Override
    public void deleteComment(Long commentToDelete) {
        newsRepository.deleteById(commentToDelete);
    }
}
