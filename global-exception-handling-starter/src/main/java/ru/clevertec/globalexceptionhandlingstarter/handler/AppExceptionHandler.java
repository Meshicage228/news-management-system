package ru.clevertec.globalexceptionhandlingstarter.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.globalexceptionhandlingstarter.dto.ExceptionResponse;
import ru.clevertec.globalexceptionhandlingstarter.exception.comment.CommentNotFoundException;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.FailedToCreateNewsException;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.*;

@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CommentNotFoundException.class)
    public ExceptionResponse handleCommentNotFoundException(CommentNotFoundException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.builder()
                .status(NOT_FOUND.value())
                .message(e.getMessage())
                .timestamp(LocalDate.now())
                .build();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NewsNotFoundException.class)
    public ExceptionResponse handleNewsNotFoundException(NewsNotFoundException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.builder()
                .status(NOT_FOUND.value())
                .message(e.getMessage())
                .timestamp(LocalDate.now())
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FailedToCreateNewsException.class)
    public ExceptionResponse failedToCreateResource(FailedToCreateNewsException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .timestamp(LocalDate.now())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(defaultMessage);

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(defaultMessage)
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
