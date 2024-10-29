package ru.clevertec.core.dto.comment;

import lombok.Data;

@Data
public class UpdateCommentDto {
    private Long newsId;
    private String content;
}
