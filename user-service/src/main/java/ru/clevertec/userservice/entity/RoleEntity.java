package ru.clevertec.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.userservice.enums.Role;

/**
 * Сущность роли, представляющая таблицу "roles" в базе данных.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {

    /**
     * Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Название роли, представленное в виде перечисления {@link Role}.
     * <p>
     * Это поле хранит строковое значение роли.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private Role role;
}
