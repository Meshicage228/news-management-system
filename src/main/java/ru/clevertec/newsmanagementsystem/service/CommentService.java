package ru.clevertec.newsmanagementsystem.service;

import ru.clevertec.newsmanagementsystem.dto.comment.CreateCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.CreatedCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.UpdateCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.UpdatedCommentDto;

import java.util.List;

public interface CommentService {
    List<CreatedCommentDto> getAllNews(Integer pageNo, Integer pageSize);

    CreatedCommentDto createComment(Long newsSource, CreateCommentDto createCommentDto);

    UpdatedCommentDto partCommentUpdate(Long newsSource, Long commentToUpdate, UpdateCommentDto updateCommentDto);

    void deleteComment(Long newsSource, Long commentToDelete);
}
