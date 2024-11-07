package ru.clevertec.api.service.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import ru.clevertec.api.clients.UserClient;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.dto.user.UserResponseDto;
import ru.clevertec.api.service.SecretKeyGenerator;
import ru.clevertec.api.service.impl.TokenServiceImpl;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplModuleTest {
    private final long expiryTime = 3600000;

    @Mock
    private UserClient userClient;

    @Mock
    private SecretKeyGenerator generator;

    private SecretKey secretKey;

    @InjectMocks
    private TokenServiceImpl tokenService;


    @BeforeEach
    public void setUp() {
        secretKey = Jwts.SIG.HS256.key().build();
        when(generator.generateSecretKey()).thenReturn(secretKey);
        tokenService.init();
    }

    @Test
    @DisplayName("Create token : check created data")
    void createToken() {
        // Given
        ReflectionTestUtils.setField(tokenService, "expiryTime", expiryTime);
        UserRequestDto userRequestDto = new UserRequestDto("testUser ", "password");
        UserResponseDto userResponseDto = new UserResponseDto(1L, "testUser ", "ROLE_USER");

        when(userClient.getLoginUser(anyString(), anyString()))
                .thenReturn(userResponseDto);

        // When
        String token = tokenService.createToken(userRequestDto);
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();

        Claims claims = (Claims) parser.parse(token).getPayload();

        // Then
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
        assertEquals("testUser ", claims.getSubject());
        assertEquals("ROLE_USER", claims.get("role"));
        assertEquals("testUser ", claims.get("username"));
    }

    @Test
    @DisplayName("Info from jwt authentication")
    void fromTokenAuthenticationInfo() {
        // Given
        String username = "testUser";
        String role = "USER";
        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("username", username)
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(secretKey)
                .compact();

        // When
        Authentication authentication = tokenService.fromToken(token);

        // Then
        assertNotNull(authentication);
        assertEquals(username, authentication.getName());
        assertEquals(1, authentication.getAuthorities().size());
        assertEquals("ROLE_USER", authentication.getAuthorities().iterator().next().getAuthority());
    }
}