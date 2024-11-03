package ru.clevertec.globalexceptionhandlingstarter.exception.news;

/**
 * Исключение, которое выбрасывается, когда не удалось создать новый новостной ресурс.
 */
public class FailedToCreateNewsException extends RuntimeException {
    private static final String message = "Failed to create new news resource";

    public FailedToCreateNewsException() {
        super(message);
    }
}
