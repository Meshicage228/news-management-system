package ru.clevertec.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.clevertec.api", "ru.clevertec.globalexceptionhandlingstarter", "ru.clevertec.loggingstarter", "ru.clevertec.cacheservice"})
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
