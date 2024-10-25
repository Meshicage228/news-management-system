package ru.clevertec.core.service;

import ru.clevertec.core.dto.comment.CreateCommentDto;
import ru.clevertec.core.dto.comment.CreatedCommentDto;
import ru.clevertec.core.dto.comment.UpdateCommentDto;
import ru.clevertec.core.dto.comment.UpdatedCommentDto;

import java.util.List;

public interface CommentService {
    List<CreatedCommentDto> getAllNews(Integer pageNo, Integer pageSize);

    CreatedCommentDto createComment(Long newsSource, CreateCommentDto createCommentDto);

    UpdatedCommentDto partCommentUpdate(Long newsSource, Long commentToUpdate, UpdateCommentDto updateCommentDto);

    void deleteComment(Long newsSource, Long commentToDelete);
}
