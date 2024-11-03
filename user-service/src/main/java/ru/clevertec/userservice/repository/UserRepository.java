package ru.clevertec.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.userservice.entity.UserEntity;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Находит пользователя по его имени.
     *
     * @param username имя пользователя, которого необходимо найти.
     * @return {@link Optional} содержащий найденную {@link UserEntity}.
     */
    Optional<UserEntity> findByUsername(String username);
}
