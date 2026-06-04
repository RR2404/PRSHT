package moe.prashast.security.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String PASSPHRASE = "MySecretPassword";

    public static String decrypt(String encryptedText) throws Exception {

        String[] parts = encryptedText.split(":");

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] iv = Base64.getDecoder().decode(parts[1]);
        byte[] cipherText = Base64.getDecoder().decode(parts[2]);

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec spec = new PBEKeySpec(
                PASSPHRASE.toCharArray(),
                salt,
                65536,
                256
        );

        SecretKey tmp = factory.generateSecret(spec);

        SecretKeySpec secretKey =
                new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher =
                Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                new IvParameterSpec(iv)
        );

        byte[] decrypted =
                cipher.doFinal(cipherText);

        return new String(decrypted);
    }
    
    public static String encrypt(String plainText) throws Exception {

        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);

        byte[] iv = new byte[16];
        random.nextBytes(iv);

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec spec = new PBEKeySpec(
        		PASSPHRASE.toCharArray(),
                salt,
                65536,
                256
        );

        SecretKey tmp = factory.generateSecret(spec);

        SecretKeySpec secretKey =
                new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher =
                Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                new IvParameterSpec(iv)
        );

        byte[] encrypted =
                cipher.doFinal(
                        plainText.getBytes(StandardCharsets.UTF_8)
                );

        return Base64.getEncoder().encodeToString(salt)
                + ":"
                + Base64.getEncoder().encodeToString(iv)
                + ":"
                + Base64.getEncoder().encodeToString(encrypted);
    }
}
