package ru.clevertec.api.service.impl;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import ru.clevertec.api.service.SecretKeyGenerator;

import javax.crypto.SecretKey;

@Service
public class SecretKeyGeneratorHS256 implements SecretKeyGenerator {
    @Override
    public SecretKey generateSecretKey() {
        return Jwts.SIG.HS256.key().build();
    }
}
