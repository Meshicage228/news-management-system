package ru.clevertec.core.exception.abstr;

import lombok.Getter;

@Getter
public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
