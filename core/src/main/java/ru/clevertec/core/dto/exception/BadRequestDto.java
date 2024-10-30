package ru.clevertec.core.dto.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BadRequestDto {
    private int status;
    private List<String> exceptionMessages;
}
