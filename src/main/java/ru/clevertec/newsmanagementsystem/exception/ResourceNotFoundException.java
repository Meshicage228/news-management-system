package ru.clevertec.newsmanagementsystem.exception;

import lombok.Getter;

@Getter
public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
