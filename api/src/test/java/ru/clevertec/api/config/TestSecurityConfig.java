package ru.clevertec.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TestSecurityConfig  {
    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {

        return http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests().anyRequest().permitAll()
                .and().build();
    }
}
