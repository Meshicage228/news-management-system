package ru.clevertec.api.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class SecretKeyGeneratorImpl implements SecretKeyGenerator {
    @Override
    public SecretKey generateSecretKey() {
        return Jwts.SIG.HS256.key().build();
    }
}
