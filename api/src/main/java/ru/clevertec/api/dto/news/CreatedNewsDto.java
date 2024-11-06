package ru.clevertec.api.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedNewsDto {
    private Long id;
    private String title;
    private String text;
    private LocalDate time;
    private String authorName;
}
