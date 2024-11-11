package ru.clevertec.api.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания новости.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsDto {

    /**
     * Заголовок новости.
     */
    @NotBlank(message = "Provide news title")
    private String title;

    /**
     * Текст новости.
     */
    @NotBlank(message = "Provide news text")
    private String text;

    /**
     * Имя автора новости.
     */
    @NotBlank(message = "Provide news authorName")
    @JsonProperty(value = "author_name")
    private String authorName;
}
