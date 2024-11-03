package ru.clevertec.api.service;

import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;

/**
 * Интерфейс сервиса для работы с комментариями.
 * Обеспечивает операции создания, обновления и удаления комментариев.
 */
public interface CommentService {

    /**
     * Создает новый комментарий для указанной новости.
     *
     * @param newsSource Идентификатор новости, к которой относится комментарий.
     * @param createCommentDto Объект с данными для создания комментария.
     * @return Объект, представляющий созданный комментарий.
     */
    CreatedCommentDto createComment(Long newsSource, CreateCommentDto createCommentDto);

    /**
     * Частично обновляет существующий комментарий.
     *
     * @param commentToUpdate Идентификатор комментария, который нужно обновить.
     * @param updateCommentDto Объект с данными для обновления комментария.
     * @return Объект, представляющий обновленный комментарий.
     */
    UpdatedCommentDto partCommentUpdate(Long commentToUpdate, UpdateCommentDto updateCommentDto);

    /**
     * Удаляет комментарий для указанной новости.
     *
     * @param newsSource Идентификатор новости, к которой относится комментарий.
     * @param commentToDelete Идентификатор комментария, который нужно удалить.
     */
    void deleteComment(Long newsSource, Long commentToDelete);
}
