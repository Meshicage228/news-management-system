package ru.clevertec.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.userservice.entity.RoleEntity;
import ru.clevertec.userservice.enums.Role;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями ролей.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    /**
     * Находит роль по её имени.
     *
     * @param role имя роли, которую необходимо найти.
     * @return {@link Optional} содержащий найденную {@link RoleEntity}.
     */
    Optional<RoleEntity> findByRole(Role role);
}
