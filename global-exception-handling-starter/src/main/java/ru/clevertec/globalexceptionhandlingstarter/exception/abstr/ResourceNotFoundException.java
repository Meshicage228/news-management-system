package ru.clevertec.globalexceptionhandlingstarter.exception.abstr;

public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
