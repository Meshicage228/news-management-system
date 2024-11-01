package ru.clevertec.globalexceptionhandlingstarter.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExceptionResponse {
    private String message;
    private int status;
    private LocalDate timestamp;
}
