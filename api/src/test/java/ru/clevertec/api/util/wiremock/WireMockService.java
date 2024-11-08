package ru.clevertec.api.util.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.service.impl.TokenServiceImpl;

import static ru.clevertec.api.util.FileReaderUtil.readFile;

@TestComponent
public class WireMockService {
    private final TokenServiceImpl tokenService;

    public WireMockService(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }

    public String setupUserAndGetToken(String username, String password, String role) throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto(username, password);

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/v1/users/login"))
                .withQueryParam("username", WireMock.equalTo(userRequestDto.getUsername()))
                .withQueryParam("password", WireMock.equalTo(userRequestDto.getPassword()))
                .willReturn(WireMock.okJson(readFile("/user/test-user.json"))
                        .withTransformers("response-template")
                        .withTransformerParameter("role", role)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        return tokenService.createToken(userRequestDto);
    }
}
