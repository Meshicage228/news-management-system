package ru.clevertec.api.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.api.dto.user.UserResponseDto;

@FeignClient(
        name = "${app.clients.user.client-name}",
        url = "${app.clients.user.url}",
        path = "${app.clients.user.client-path}"
)
public interface UserClient {

    @GetMapping("/login")
    UserResponseDto getLoginUser(@RequestParam String username, @RequestParam String password);
}
