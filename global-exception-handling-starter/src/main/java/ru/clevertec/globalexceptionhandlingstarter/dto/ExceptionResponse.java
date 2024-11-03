package ru.clevertec.globalexceptionhandlingstarter.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Класс, представляющий ответ об исключении.
 *
 */
@Data
@Builder
public class ExceptionResponse {
    /**
     * Сообщение об ошибке.
     */
    private String message;

    /**
     * Статус HTTP, соответствующий ошибке.
     */
    private int status;

    /**
     * Дата и время, когда произошла ошибка.
     */
    private LocalDate timestamp;
}
