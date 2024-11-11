package ru.clevertec.api.service.impl;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import ru.clevertec.api.service.SecretKeyGenerator;

import javax.crypto.SecretKey;

/**
 * Реализация генератора секретного ключа для алгоритма HS256.
 * <p>
 * Этот класс отвечает за создание секретного ключа, который будет использоваться
 * для подписи JWT токенов
 * </p>
 */
@Service
public class SecretKeyGeneratorHS256 implements SecretKeyGenerator {

    /**
     * Генерирует секретный ключ для алгоритма HS256.
     *
     * @return сгенерированный секретный ключ
     */
    @Override
    public SecretKey generateSecretKey() {
        return Jwts.SIG.HS256.key().build();
    }
}
