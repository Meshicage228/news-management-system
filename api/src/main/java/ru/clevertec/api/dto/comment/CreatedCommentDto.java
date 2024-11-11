package ru.clevertec.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO для созданного комментария.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedCommentDto {

    /**
     * Уникальный id комментария.
     */
    private Long id;

    /**
     * Время создания комментария.
     */
    private LocalDate time;

    /**
     * Текст комментария.
     */
    private String text;

    /**
     * Имя автора комментария.
     */
    private String authorName;
}
