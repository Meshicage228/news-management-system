package ru.clevertec.core.service;

import ru.clevertec.core.dto.comment.*;

public interface CommentService {
    void createComment(Long newsSource, CreateCommentDto createCommentDto);

    UpdatedCommentDto partCommentUpdate(Long commentToUpdate, UpdateCommentDto updateCommentDto);

    void deleteComment(Long newsSource, Long commentToDelete);
}
