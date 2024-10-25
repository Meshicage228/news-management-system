package ru.clevertec.core.exception;

import lombok.Getter;

@Getter
public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
