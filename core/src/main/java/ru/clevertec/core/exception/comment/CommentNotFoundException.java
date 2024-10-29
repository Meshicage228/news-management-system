package ru.clevertec.core.exception.comment;

import ru.clevertec.core.exception.abstr.ResourceNotFoundException;

public class CommentNotFoundException extends ResourceNotFoundException {
    private static final String message = "Comment with id : %s not found";

    public CommentNotFoundException(Long id) {
        super(String.format(message, id));
    }
}
