package ru.clevertec.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.clevertec.userservice.dto.CreateUserDto;
import ru.clevertec.userservice.dto.UserResponseDto;
import ru.clevertec.userservice.entity.UserEntity;

/**
 * Маппер для преобразования объектов между {@link CreateUserDto}, {@link UserEntity} и {@link UserResponseDto}.
 * <p>
 * Этот класс предоставляет методы для преобразования данных пользователя, включая кодирование паролей.
 * </p>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Преобразует объект {@link CreateUserDto} в {@link UserEntity}.
     *
     * @param createUserDto объект, содержащий данные для создания пользователя.
     * @return преобразованный объект {@link UserEntity}.
     */
    @Mapping(target = "password", source = "password", qualifiedByName = "getEncodedPassword")
    public abstract UserEntity toEntity(CreateUserDto createUserDto);

    /**
     * Кодирует пароль с использованием {@link BCryptPasswordEncoder}.
     *
     * @param password пароль, который необходимо закодировать.
     * @return закодированный пароль.
     */
    @Named("getEncodedPassword")
    public String encodePassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Преобразует объект {@link UserEntity} в {@link UserResponseDto}.
     *
     * @param userEntity объект пользователя, который необходимо преобразовать.
     * @return преобразованный объект {@link UserResponseDto}.
     */
    @Mapping(target = "role", source = "roleEntity.role")
    public abstract UserResponseDto toDto(UserEntity userEntity);
}
