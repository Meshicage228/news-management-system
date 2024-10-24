package ru.clevertec.newsmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.newsmanagementsystem.dto.comment.CreateCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.CreatedCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.UpdateCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.UpdatedCommentDto;
import ru.clevertec.newsmanagementsystem.mapper.CommentMapper;
import ru.clevertec.newsmanagementsystem.repository.CommentsRepository;
import ru.clevertec.newsmanagementsystem.service.CommentService;

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
