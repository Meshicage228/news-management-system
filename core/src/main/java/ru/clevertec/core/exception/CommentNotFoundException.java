package ru.clevertec.core.exception;

public class CommentNotFoundException extends ResourceNotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
