package ru.clevertec.core.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentDto {
    @NotBlank(message = "Comment cannot be blank")
    @NotNull(message = "Provide comment")
    private String content;

    @JsonProperty(value = "author_name")
    private String author;
}
