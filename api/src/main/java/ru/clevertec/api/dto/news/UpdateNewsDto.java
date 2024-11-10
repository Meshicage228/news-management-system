package ru.clevertec.api.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.api.util.FullUpdateNewsMarker;

/**
 * DTO для обновления информации о новости.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNewsDto {

    /**
     * Заголовок новости.
     *
     * @see FullUpdateNewsMarker
     */
    @NotBlank(message = "News title cannot be blank", groups = FullUpdateNewsMarker.class)
    @NotNull(message = "Provide news title", groups = FullUpdateNewsMarker.class)
    private String title;

    /**
     * Текст новости.
     *
     * @see FullUpdateNewsMarker
     */
    @NotNull(message = "Provide news content!", groups = FullUpdateNewsMarker.class)
    @NotBlank(message = "News content cannot be blank", groups = FullUpdateNewsMarker.class)
    private String text;

    /**
     * Имя автора новости.
     * @see FullUpdateNewsMarker
     */
    @NotNull(message = "Provide news content!", groups = FullUpdateNewsMarker.class)
    @NotBlank(message = "News content cannot be blank", groups = FullUpdateNewsMarker.class)
    @JsonProperty(value = "author_name")
    private String authorName;
}
