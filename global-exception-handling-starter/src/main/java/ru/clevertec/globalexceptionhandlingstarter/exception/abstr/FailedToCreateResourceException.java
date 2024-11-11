package ru.clevertec.globalexceptionhandlingstarter.exception.abstr;

public abstract class FailedToCreateResourceException extends RuntimeException {
    public FailedToCreateResourceException(String message) {
        super(message);
    }
}
