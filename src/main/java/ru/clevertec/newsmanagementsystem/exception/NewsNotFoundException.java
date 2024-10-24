package ru.clevertec.newsmanagementsystem.exception;

public class NewsNotFoundException extends ResourceNotFoundException {
    public NewsNotFoundException(String message) {
        super(message);
    }
}
