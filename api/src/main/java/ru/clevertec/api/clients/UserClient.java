package ru.clevertec.api.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.api.dto.user.UserResponseDto;

/**
 * Клиент для взаимодействия с сервисом пользователей.
 */
@FeignClient(
        name = "${app.clients.user.client-name}",
        url = "${app.clients.user.url}",
        path = "${app.clients.user.client-path}"
)
public interface UserClient {

    /**
     * Получает информацию о пользователе для аутентификации.
     *
     * @param username имя пользователя для аутентификации
     * @param password пароль пользователя для аутентификации
     * @return объект {@link UserResponseDto}, содержащий информацию о пользователе
     */
    @GetMapping("/login")
    UserResponseDto getLoginUser (@RequestParam String username, @RequestParam String password);
}
