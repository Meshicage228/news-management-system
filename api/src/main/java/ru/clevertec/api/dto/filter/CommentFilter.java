package ru.clevertec.api.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentFilter {
    @NotNull(message = "provide news id")
    @JsonProperty(value = "news_id")
    private Long newsId;
    private String text;
    private String authorName;
}
