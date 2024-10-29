package ru.clevertec.core.dto.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentFilter {
    @NotNull(message = "provide news id")
    private Long newsId;
    private String text;
    private String authorName;
}
