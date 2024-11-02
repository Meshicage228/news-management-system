package ru.clevertec.globalexceptionhandlingstarter.exception.comment;

public class CommentNotFoundException extends RuntimeException {
    private static final String message = "Comment with id : %s not found";

    public CommentNotFoundException(Long id) {
        super(String.format(message, id));
    }
}
