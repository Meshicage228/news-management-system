package ru.clevertec.globalexceptionhandlingstarter.exception.news;

public class NewsNotFoundException extends RuntimeException{
    private static final String message = "News with id : %s not found";

    public NewsNotFoundException(Long id) {
        super(String.format(message, id));
    }
}
