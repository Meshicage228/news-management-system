package ru.clevertec.api.dto.filter;

import lombok.Builder;
import lombok.Data;

/**
 * Фильтр для поиска новостей.
 */
@Data
@Builder
public class NewsFilter {

    /**
     * Заголовок новости для фильтрации.
     */
    private String title;

    /**
     * Текст новости для фильтрации.
     */
    private String text;
}
