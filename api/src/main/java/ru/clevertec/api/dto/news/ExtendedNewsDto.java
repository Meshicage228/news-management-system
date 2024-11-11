package ru.clevertec.api.dto.news;

import lombok.*;
import org.springframework.data.domain.Page;
import ru.clevertec.api.dto.comment.CreatedCommentDto;

import java.time.LocalDate;

/**
 * DTO для расширенной информации о новости.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExtendedNewsDto {

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

    /**
     * Комментарии к новости.
     * @see CreatedCommentDto
     */
    @ToString.Exclude
    private Page<CreatedCommentDto> comments;
}
