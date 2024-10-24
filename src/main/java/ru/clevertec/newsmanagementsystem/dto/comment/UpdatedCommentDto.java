package ru.clevertec.newsmanagementsystem.dto.comment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatedCommentDto {
    private Long id;
    private LocalDate time;
    private String content;
    private String author;
}
