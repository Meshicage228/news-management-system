package ru.clevertec.core.dto.comment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatedCommentDto {
    private Long id;
    private LocalDate time;
    private String content;
    private String author;
}
