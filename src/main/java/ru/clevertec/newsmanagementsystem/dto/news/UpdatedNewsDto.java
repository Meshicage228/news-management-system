package ru.clevertec.newsmanagementsystem.dto.news;

import lombok.Data;

@Data
public class UpdatedNewsDto {
    private Long id;
    private String title;
    private String text;
    private String author;
}
