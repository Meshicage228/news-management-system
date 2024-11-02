package ru.clevertec.api.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentFilter {
    @NotNull(message = "provide news id")
    @JsonProperty(value = "news_id")
    private long newsId;
    private String text;
    private String authorName;
}
