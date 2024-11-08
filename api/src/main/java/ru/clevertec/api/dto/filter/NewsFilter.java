package ru.clevertec.api.dto.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsFilter {
    private String title;
    private String text;
}
