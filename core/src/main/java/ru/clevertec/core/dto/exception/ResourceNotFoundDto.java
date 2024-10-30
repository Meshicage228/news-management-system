package ru.clevertec.core.dto.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceNotFoundDto {
    private String resourceName;
    private String message;
}
