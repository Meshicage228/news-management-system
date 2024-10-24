package ru.clevertec.newsmanagementsystem.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatedCommentDto {
    private Long id;
    private LocalDate time;
    private String content;
    @JsonProperty(value = "author_name")
    private String author;
}
