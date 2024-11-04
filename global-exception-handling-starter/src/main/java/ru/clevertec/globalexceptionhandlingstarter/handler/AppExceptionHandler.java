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
import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.FailedToCreateResourceException;
import ru.clevertec.globalexceptionhandlingstarter.exception.abstr.ResourceNotFoundException;
import ru.clevertec.globalexceptionhandlingstarter.exception.user.IncorrectCredentialsException;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.*;

/**
 * Обработчик исключений приложения
 */
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Обработчик исключений для {@link ResourceNotFoundException} и его наследников.
     *
     * <p>При возникновении этого исключения возвращается ответ с статусом 404
     * и сообщением об ошибке.</p>
     *
     * @param e исключение, которое было выброшено
     * @return объект {@link ExceptionResponse} с информацией об ошибке
     */
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

    /**
     * Обработчик исключений для {@link IncorrectCredentialsException}.
     *
     * <p>Возникает, когда не совпадает пароль с паролем в бд.</p>
     *
     * @param e исключение, которое было выброшено
     * @return объект {@link ExceptionResponse} с информацией об ошибке
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ExceptionResponse incorrectCredentialsHandling(IncorrectCredentialsException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.builder()
                .status(BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(LocalDate.now())
                .build();
    }

    /**
     * Обработчик исключений для {@link FailedToCreateResourceException} и его наследников.
     *
     * <p>При возникновении этого исключения возвращается ответ с статусом 500
     * и сообщением об ошибке.</p>
     *
     * @param e исключение, которое было выброшено
     * @return объект {@link ExceptionResponse} с информацией об ошибке
     */
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

    /**
     * Обработка исключений, возникающих из-за невалидных аргументов.
     *
     * <p>Метод переопределяет поведение {@link ResponseEntityExceptionHandler} для обработки
     * исключений {@link MethodArgumentNotValidException} и возвращает ответ с статусом 400.</p>
     *
     * @param ex исключение, которое было выброшено
     * @param headers заголовки HTTP
     * @param status статус ответа
     * @param request объект запроса
     * @return объект {@link ResponseEntity} с информацией об ошибке
     */
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
