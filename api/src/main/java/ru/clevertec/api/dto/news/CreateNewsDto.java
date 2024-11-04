package ru.clevertec.api.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNewsDto {
    @NotBlank(message = "Provide news title")
    private String title;
    @NotBlank(message = "Provide news text")
    private String text;
    @NotBlank(message = "Provide news authorName")
    @JsonProperty(value = "author_name")
    private String authorName;
}
