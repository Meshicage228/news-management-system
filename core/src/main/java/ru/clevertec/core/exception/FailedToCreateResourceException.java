package ru.clevertec.core.exception;

public abstract class FailedToCreateResourceException extends RuntimeException {
    public FailedToCreateResourceException(String message) {
        super(message);
    }
}
