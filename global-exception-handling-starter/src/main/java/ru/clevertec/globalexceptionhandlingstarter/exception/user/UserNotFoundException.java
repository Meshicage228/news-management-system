package ru.clevertec.globalexceptionhandlingstarter.exception.user;

import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    private static final String message = "User with login : %s not found";

    public UserNotFoundException(String login) {
        super(String.format(message, login));
    }
}
