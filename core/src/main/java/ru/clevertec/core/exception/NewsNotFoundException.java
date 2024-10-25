package ru.clevertec.core.exception;

public class NewsNotFoundException extends ResourceNotFoundException {
    public NewsNotFoundException(String message) {
        super(message);
    }
}
