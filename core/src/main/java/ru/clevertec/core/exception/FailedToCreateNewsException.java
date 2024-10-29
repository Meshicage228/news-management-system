package ru.clevertec.core.exception;

public class FailedToCreateNewsException extends FailedToCreateResourceException {
    private static final String message = "Failed to create new news resource";

    public FailedToCreateNewsException() {
        super(message);
    }
}
