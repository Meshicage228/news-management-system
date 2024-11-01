package ru.clevertec.globalexceptionhandlingstarter.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.globalexceptionhandlingstarter.dto.ExceptionResponse;
import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.FailedToCreateResourceException;
import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.ResourceNotFoundException;

import java.time.LocalDate;

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
    public ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.builder()
                .status(NOT_FOUND.value())
                .message(e.getMessage())
                .timestamp(LocalDate.now())
                .build();
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FailedToCreateResourceException.class)
    public ExceptionResponse failedToCreateResource(FailedToCreateResourceException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .timestamp(LocalDate.now())
                .build();
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error(defaultMessage);

        return ExceptionResponse.builder()
                .status(BAD_REQUEST.value())
                .message(defaultMessage)
                .timestamp(LocalDate.now())
                .build();
    }
}
