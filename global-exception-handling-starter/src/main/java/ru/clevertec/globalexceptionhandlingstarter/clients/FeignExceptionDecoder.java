package ru.clevertec.globalexceptionhandlingstarter.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.globalexceptionhandlingstarter.dto.ExceptionResponse;
import ru.clevertec.globalexceptionhandlingstarter.exception.user.IncorrectCredentialsException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class FeignExceptionDecoder implements ErrorDecoder {

    private final static Map<Predicate<Integer>, Function<ExceptionResponse, Exception>> errorHandlers = new HashMap<>(){{
        put(value -> value == 400, errorMessage -> {
            log.error("Exception message : {}", errorMessage.getMessage());
            return new IncorrectCredentialsException();
        });
    }};

    @Override
    public Exception decode(String s, Response response) {
        try {
            byte[] arr = response.body().asInputStream().readAllBytes();
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            ExceptionResponse errorMessage = mapper.readValue(arr, ExceptionResponse.class);

            return errorHandlers.entrySet().stream()
                    .filter(entry -> entry.getKey().test(response.status()))
                    .map(entry -> entry.getValue().apply(errorMessage))
                    .findFirst()
                    .orElseThrow(() -> {
                        String errorReason = errorMessage.getMessage();
                        log.error("No handler for status : {}; exception message : {}", response.status(), errorReason);
                        return new RuntimeException(errorReason);
                    });
        } catch (IOException e) {
            String readFailed = "Failed to read error message: " + e.getMessage();
            log.error(readFailed);
            return new RuntimeException(readFailed);
        }
    }
}