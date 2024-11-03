package ru.clevertec.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность пользователя, представляющая таблицу "users" в базе данных.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "users")
public class UserEntity {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя, уникальное для каждого пользователя.
     */
    private String username;

    /**
     * Пароль пользователя, который хранится в закодированном виде.
     */
    private String password;

    /**
     * Роль пользователя, связанная с сущностью {@link RoleEntity}.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
}
