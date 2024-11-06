package ru.clevertec.api.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedNewsDto {
    private Long id;
    private String title;
    private String text;
    private String authorName;
}
