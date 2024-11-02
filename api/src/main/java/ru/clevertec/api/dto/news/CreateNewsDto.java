package ru.clevertec.api.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNewsDto {
    @NotBlank(message = "News title cannot be blank")
    @NotNull(message = "Provide news title")
    private String title;
    @NotNull(message = "Provide news content!")
    @NotBlank(message = "News content cannot be blank")
    private String text;
}
