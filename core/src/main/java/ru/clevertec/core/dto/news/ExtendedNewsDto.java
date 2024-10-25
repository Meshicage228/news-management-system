package ru.clevertec.core.dto.news;

import lombok.*;
import ru.clevertec.core.dto.comment.CreatedCommentDto;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExtendedNewsDto {
    private Long id;
    private String title;
    private String text;
    private LocalDate time;
    private String author;
    @ToString.Exclude
    private List<CreatedCommentDto> comments;
}
