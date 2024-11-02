package ru.clevertec.api.service;

import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;

public interface CommentService {
    void createComment(Long newsSource, CreateCommentDto createCommentDto);

    UpdatedCommentDto partCommentUpdate(Long commentToUpdate, UpdateCommentDto updateCommentDto);

    void deleteComment(Long newsSource, Long commentToDelete);
}
