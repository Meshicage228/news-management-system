package ru.clevertec.globalexceptionhandlingstarter.exception.user;

import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.FailedToCreateResourceException;

public class FailedToCreateUserException extends FailedToCreateResourceException {
    private static final String message = "Failed to create new user resource";

    public FailedToCreateUserException() {
        super(message);
    }
}
