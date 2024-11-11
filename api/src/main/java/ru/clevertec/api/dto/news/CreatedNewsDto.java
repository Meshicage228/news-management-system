package ru.clevertec.api.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO для созданной новости.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedNewsDto {

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
     * Время создания новости.
     */
    private LocalDate time;

    /**
     * Имя автора новости.
     */
    private String authorName;
}
