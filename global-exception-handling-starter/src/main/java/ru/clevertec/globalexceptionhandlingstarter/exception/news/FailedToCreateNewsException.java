package ru.clevertec.globalexceptionhandlingstarter.exception.news;

import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.FailedToCreateResourceException;

/**
 * Исключение, которое выбрасывается, когда не удалось создать новый новостной ресурс.
 */
public class FailedToCreateNewsException extends FailedToCreateResourceException {
    private static final String message = "Failed to create new news resource";

    public FailedToCreateNewsException() {
        super(message);
    }
}
