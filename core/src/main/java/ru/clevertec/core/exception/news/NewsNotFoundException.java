package ru.clevertec.core.exception.news;

import ru.clevertec.core.exception.abstr.ResourceNotFoundException;

public class NewsNotFoundException extends ResourceNotFoundException {
    private static final String message = "News with id : %s not found";

    public NewsNotFoundException(Long id) {
        super(String.format(message, id));
    }
}
