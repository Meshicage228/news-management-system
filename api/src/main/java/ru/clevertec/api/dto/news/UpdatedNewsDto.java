package ru.clevertec.api.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для обновленной информации о новости.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedNewsDto {

    /**
     * Уникальный id новости.
     */
    private Long id;

    /**
     * Заголовок новости.
     */
    private String title;

    /**
     * Текст новости.
     */
    private String text;

    /**
     * Имя автора новости.
     */
    private String authorName;
}
