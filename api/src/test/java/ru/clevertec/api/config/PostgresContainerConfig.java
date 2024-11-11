package ru.clevertec.api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public abstract class PostgresContainerConfig {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer(DynamicPropertyRegistry registry) {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.3-alpine")
                .withDatabaseName("user-api-test")
                .withUsername("user")
                .withInitScript("db/create-tables.sql")
                .withPassword("pass");

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");

        postgreSQLContainer.start();
        return postgreSQLContainer;
    }
}
