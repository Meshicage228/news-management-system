package ru.clevertec.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.core.dto.exception.BadRequestDto;
import ru.clevertec.core.dto.exception.ResourceNotFoundDto;
import ru.clevertec.core.exception.abstr.FailedToCreateResourceException;
import ru.clevertec.core.exception.abstr.ResourceNotFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        log.error(e.getMessage(), e);
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResourceNotFoundDto handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResourceNotFoundDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FailedToCreateResourceException.class)
    public ResourceNotFoundDto failedToCreateResource(FailedToCreateResourceException e) {
        log.error(e.getMessage(), e);
        return ResourceNotFoundDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BadRequestDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> collected = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        log.error(collected.toString());

        return BadRequestDto.builder()
                .exceptionMessages(collected)
                .build();
    }
}
