package ru.clevertec.api.service;

import javax.crypto.SecretKey;

/**
 * Интерфейс для генерации секретного ключа.
 */
public interface SecretKeyGenerator {

    /**
     * Генерирует секретный ключ.
     *
     * @return сгенерированный секретный ключ
     */
    SecretKey generateSecretKey();
}
