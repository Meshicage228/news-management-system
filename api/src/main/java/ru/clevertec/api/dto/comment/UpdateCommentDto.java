package ru.clevertec.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для обновления комментария.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentDto {

    /**
     * Новый текст комментария.
     */
    private String text;
}
