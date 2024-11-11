package ru.clevertec.api.dto.news;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO для краткой информации о новости.
 */
@Data
public class ShortNewsDto {

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
