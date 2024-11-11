package ru.clevertec.globalexceptionhandlingstarter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Класс, представляющий ответ об исключении.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
