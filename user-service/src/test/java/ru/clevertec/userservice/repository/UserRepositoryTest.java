package ru.clevertec.userservice.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.userservice.config.PostgresContainerConfig;
import ru.clevertec.userservice.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgresContainerConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/insert-data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/clean-up.sql")
@DisplayName("User repository tests")
class UserRepositoryTest extends PostgresContainerConfig {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("find user by username successfully")
    void testFindByUsername() {
        // Given
        String username = "Vlad";

        // When
        Optional<UserEntity> user = userRepository.findByUsername(username);

        // Then
        assertThat(user.isPresent()).isTrue();
        UserEntity userEntity = user.get();
        assertThat(userEntity.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("create user successfully")
    void testCreateUser() {
        // Given
        UserEntity user = new UserEntity();
        user.setUsername("John");
        user.setPassword("password");

        // When
        UserEntity savedUser = userRepository.save(user);
        List<UserEntity> allUsers = userRepository.findAll();

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("John");
        assertThat(allUsers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("update existing user successfully")
    void testUpdateUser() {
        // Given
        UserEntity user = userRepository.findByUsername("Vlad").get();
        user.setUsername("Vladimir");

        // When
        UserEntity updatedUser = userRepository.save(user);

        // Then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo("Vladimir");
    }

    @Test
    @DisplayName("delete existing user")
    void testDeleteUser() {
        // Given
        Long userId = 1L;
        userRepository.deleteById(userId);

        // When
        List<UserEntity> allUsers = userRepository.findAll();

        // Then
        assertThat(allUsers.size()).isEqualTo(0);
    }
}