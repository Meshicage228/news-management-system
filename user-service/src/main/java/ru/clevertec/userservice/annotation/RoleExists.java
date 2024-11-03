package ru.clevertec.userservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.clevertec.userservice.annotation.validator.CheckRoleIsExistsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CheckRoleIsExistsValidator.class})
public @interface RoleExists {
    String message() default "Полученная роль не найдена";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
