package ru.clevertec.api.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsDto {
    @NotBlank(message = "Provide news title")
    private String title;
    @NotBlank(message = "Provide news text")
    private String text;
    @NotBlank(message = "Provide news authorName")
    @JsonProperty(value = "author_name")
    private String authorName;
}
