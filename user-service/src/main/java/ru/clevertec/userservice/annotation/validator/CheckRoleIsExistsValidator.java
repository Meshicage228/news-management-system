package ru.clevertec.userservice.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.userservice.annotation.RoleExists;
import ru.clevertec.userservice.enums.Role;
import ru.clevertec.userservice.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class CheckRoleIsExistsValidator implements ConstraintValidator<RoleExists, Role> {
    private final RoleRepository roleRepository;

    @Override
    public boolean isValid(Role value, ConstraintValidatorContext context) {
        return roleRepository.findByRole(value)
                .isPresent();
    }
}
