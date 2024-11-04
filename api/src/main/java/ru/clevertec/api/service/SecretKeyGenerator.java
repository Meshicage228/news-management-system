package ru.clevertec.api.service;

import javax.crypto.SecretKey;

public interface SecretKeyGenerator {
    SecretKey generateSecretKey();
}
