package ru.clevertec.newsmanagementsystem.exception;

public class CommentNotFoundException extends ResourceNotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
