package ru.clevertec.api.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDto {
    @NotEmpty(message = "Comment may not be empty")
    @Size(min = 2, max = 32, message = "Comment must be between 2 and 255 characters long")
    private String text;

    @JsonProperty(value = "author_name")
    private String authorName;
}
