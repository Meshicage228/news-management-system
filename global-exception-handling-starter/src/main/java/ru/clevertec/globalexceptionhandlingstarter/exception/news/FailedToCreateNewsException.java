package ru.clevertec.globalexceptionhandlingstarter.exception.news;

public class FailedToCreateNewsException extends RuntimeException {
    private static final String message = "Failed to create new news resource";

    public FailedToCreateNewsException() {
        super(message);
    }
}
