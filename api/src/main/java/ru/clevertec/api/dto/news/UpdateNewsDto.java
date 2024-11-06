package ru.clevertec.api.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.api.util.FullUpdateNewsMarker;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNewsDto {
    @NotBlank(message = "News title cannot be blank", groups = FullUpdateNewsMarker.class)
    @NotNull(message = "Provide news title", groups = FullUpdateNewsMarker.class)
    private String title;
    @NotNull(message = "Provide news content!", groups = FullUpdateNewsMarker.class)
    @NotBlank(message = "News content cannot be blank", groups = FullUpdateNewsMarker.class)
    private String text;
    @NotNull(message = "Provide news content!", groups = FullUpdateNewsMarker.class)
    @NotBlank(message = "News content cannot be blank", groups = FullUpdateNewsMarker.class)
    @JsonProperty(value = "author_name")
    private String authorName;
}
