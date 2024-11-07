package ru.clevertec.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedCommentDto {
    private Long id;
    private LocalDate time;
    private String text;
    private String authorName;
}
