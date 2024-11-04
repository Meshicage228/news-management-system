package ru.clevertec.globalexceptionhandlingstarter.exception.news;

import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.ResourceNotFoundException;

/**
 * Исключение, которое выбрасывается, когда новость с указанным идентификатором не найдена.
 */
public class NewsNotFoundException extends ResourceNotFoundException {
    private static final String message = "News with id : %s not found";

    /**
     * @param id идентификатор новости, которая не найдена
     */
    public NewsNotFoundException(Long id) {
        super(String.format(message, id));
    }
}
