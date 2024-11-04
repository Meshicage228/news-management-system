package ru.clevertec.api.dto.news;

import lombok.Data;

@Data
public class UpdatedNewsDto {
    private Long id;
    private String title;
    private String text;
    private String authorName;
}
