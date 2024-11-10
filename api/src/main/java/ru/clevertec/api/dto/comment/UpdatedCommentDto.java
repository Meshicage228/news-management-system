package ru.clevertec.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO для обновленного комментария.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedCommentDto {

    /**
     * Уникальный id комментария.
     */
    private Long id;

    /**
     * Время обновления комментария.
     */
    private LocalDate time;

    /**
     * Новый текст комментария.
     */
    private String text;

    /**
     * Имя автора комментария.
     */
    private String authorName;
}
