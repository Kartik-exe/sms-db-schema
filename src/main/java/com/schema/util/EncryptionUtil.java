package com.schema.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "ENCRYPTION_SECRET_KEY";
    private static final int SECRET_KEY_LENGTH_BYTES = 32;

    private static SecretKeySpec getSecretKey() {

        String raw = System.getenv(SECRET_KEY);
        if (raw == null || raw.isBlank()) {
            throw new IllegalStateException("Missing or Empty environment variable" + SECRET_KEY + " (required for EncryptionUtil)");
        }
        String secret = raw.trim();
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (secret.length() != SECRET_KEY_LENGTH_BYTES || keyBytes.length != SECRET_KEY_LENGTH_BYTES) {
            throw new IllegalStateException(
                    SECRET_KEY + " must be exactly 32 characters encoding to 32 UTF-8 bytes (AES-256); CHARS="
                            + secret.length() + ", utf8Bytes=" + keyBytes.length);
        }
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
        byte[] decodeBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodeBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
