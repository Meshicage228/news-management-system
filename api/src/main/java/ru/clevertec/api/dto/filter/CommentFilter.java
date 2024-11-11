package ru.clevertec.api.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Фильтр для поиска комментариев.
 */
@Data
@Builder
public class CommentFilter {

    /**
     * Уникальный id новости.
     */
    @NotNull(message = "provide news id")
    @JsonProperty(value = "news_id")
    private Long newsId;

    /**
     * Текст комментария для фильтрации.
     */
    private String text;

    /**
     * Имя автора комментария для фильтрации.
     */
    private String authorName;
}
