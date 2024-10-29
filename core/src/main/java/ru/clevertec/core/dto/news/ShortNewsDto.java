package ru.clevertec.core.dto.news;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ShortNewsDto {
    private Long id;
    private String title;
    private String text;
    private LocalDate time;
    private String author;
}
