package com.riyaz.ecom.userauthservice.configs;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthConfigTest {

    private static final String TEST_SECRET = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

    private final AuthConfig authConfig = new AuthConfig();

    @Test
    void signingKeyIsStableForTheSameConfiguredSecret() {
        SecretKey firstKey = authConfig.jwtSigningKey(TEST_SECRET);
        SecretKey secondKey = authConfig.jwtSigningKey(TEST_SECRET);

        assertArrayEquals(firstKey.getEncoded(), secondKey.getEncoded());
    }

    @Test
    void rejectsSecretsThatAreTooShort() {
        assertThrows(IllegalStateException.class, () -> authConfig.jwtSigningKey("c2hvcnQ="));
    }
}
