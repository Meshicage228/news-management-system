package ru.clevertec.api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.clevertec.api.service.impl.TokenServiceImpl;
import ru.clevertec.api.util.wiremock.WireMockService;

@TestConfiguration
public class WireMockConfig {
    @Bean
    public WireMockService wireMockServer(TokenServiceImpl tokenService) {
        return new WireMockService(tokenService);
    }
}
