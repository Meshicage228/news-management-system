package ru.clevertec.core.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.core.dto.comment.CreateCommentDto;
import ru.clevertec.core.dto.comment.CreatedCommentDto;
import ru.clevertec.core.dto.comment.UpdateCommentDto;
import ru.clevertec.core.dto.comment.UpdatedCommentDto;
import ru.clevertec.core.mapper.CommentMapper;
import ru.clevertec.core.repository.CommentsRepository;
import ru.clevertec.core.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentsRepository commentsRepository;

    @Override
    public List<CreatedCommentDto> getAllNews(Integer pageNo, Integer pageSize) {
        return List.of();
    }

    @Override
    public CreatedCommentDto createComment(Long newsSource, CreateCommentDto createCommentDto) {
        return null;
    }

    @Override
    public UpdatedCommentDto partCommentUpdate(Long newsSource, Long commentToUpdate, UpdateCommentDto updateCommentDto) {
        return null;
    }

    @Override
    public void deleteComment(Long newsSource, Long commentToDelete) {

    }
}
