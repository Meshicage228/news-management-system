package ru.clevertec.api.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания комментария.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDto {

    /**
     * Текст комментария.
     */
    @NotEmpty(message = "Comment may not be empty")
    @Size(min = 2, max = 255, message = "Comment must be between 2 and 255 characters long")
    private String text;

    /**
     * Имя автора комментария.
     */
    @JsonProperty(value = "author_name")
    private String authorName;
}
