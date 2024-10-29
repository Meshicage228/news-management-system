package ru.clevertec.core.exception.abstr;

public abstract class FailedToCreateResourceException extends RuntimeException {
    public FailedToCreateResourceException(String message) {
        super(message);
    }
}
