package moe.prashast.util;

import moe.prashast.bean.UdiseValidationServiceBean;
import moe.prashast.dto.AuthSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.UUID;

@Service
public class UdiseValidationUtil {

    @Value("${udise.base.api.url}")
    private String checkUdiseApiUrl;

    @Value("${certificate.path}")
    private String certificatePath;

   /* @Value("${udise.app.key}")
    private String appKey;*/

    private volatile String appKey;

    @Autowired
    UdiseValidationServiceBean udiseValidationServiceBean;


    public synchronized String getOrCreateAppKey() throws NoSuchAlgorithmException {
        if (this.appKey == null) {
            this.appKey = generateAppKey();
        }
        return this.appKey;
    }

    private String generateAppKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, SecureRandom.getInstanceStrong());
        SecretKey sk = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(sk.getEncoded());
    }


//    public String getAppKey() throws NoSuchAlgorithmException {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(256, SecureRandom.getInstanceStrong());
//        SecretKey sk = keyGen.generateKey();
//        String encodedKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//       return encodedKey;
//    }

    public PublicKey getPublicKeyBase64() throws FileNotFoundException, CertificateException {

        FileInputStream fis = new FileInputStream(certificatePath);
        CertificateFactory cf = CertificateFactory.getInstance("X509");
        X509Certificate crt = (X509Certificate) cf.generateCertificate(fis);
        PublicKey publicKey = crt.getPublicKey();
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

//        return publicKeyBase64;
        return publicKey;
    }
    public String getEncriptedData(String plainJson, PublicKey publicKeyBase64) throws Exception {
        String  value = bytesToHex(encrypt(Base64.getEncoder()
                .encodeToString(plainJson.getBytes(StandardCharsets.UTF_8)), publicKeyBase64));
        return value;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes)
            result.append(String.format("%02x", b));
        return result.toString();
    }

//    public static byte[] encrypt(
//            String base64Payload,
//            String base64PublicKey) throws Exception {
//
//        // Decode public key
//        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
//
//        X509EncodedKeySpec keySpec =
//                new X509EncodedKeySpec(keyBytes);
//
//        KeyFactory keyFactory =
//                KeyFactory.getInstance("RSA");
//
//        PublicKey publicKey =
//                keyFactory.generatePublic(keySpec);
//
//        Cipher cipher =
//                Cipher.getInstance("RSA/ECB/PKCS1Padding");
//
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//        return cipher.doFinal(
//                base64Payload.getBytes(StandardCharsets.UTF_8)
//        );
//    }

    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
// get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");
// encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

//    public String decryptSek(String sek, String appKey) throws Exception {
//
//        // 1. Clean SEK (UDISE sometimes sends line breaks)
//        String cleanedSek = sek.replaceAll("\\s", "");
//
//        // 2. Base64 decode appKey (AES-256 key)
//        byte[] appKeyBytes = Base64.getDecoder().decode(appKey);
//        SecretKeySpec appKeySpec = new SecretKeySpec(appKeyBytes, "AES");
//
//        // 3. Base64 decode SEK
//        byte[] decodedSek = Base64.getMimeDecoder().decode(cleanedSek);
//
//        // 4. AES decrypt decoded SEK using appKey
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, appKeySpec);
//
//        byte[] decryptedSekBytes = cipher.doFinal(decodedSek);
//
//        // 5. Decrypted SEK is a Base64-encoded AES key (C)
//        return new String(decryptedSekBytes, StandardCharsets.UTF_8).trim();
//    }


    public static String decrypt (String text, String secretKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SecretKeySpec skey = new SecretKeySpec(decodedKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skey);
        byte[] output=cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(output);
    }


    public String encryptWithSek(String plainJson, String decryptedSek) throws Exception {

        byte[] sekBytes = Base64.getDecoder().decode(decryptedSek);
        SecretKeySpec sekKey = new SecretKeySpec(sekBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sekKey);

        byte[] encrypted = cipher.doFinal(
                plainJson.getBytes(StandardCharsets.UTF_8)
        );

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String generateUserId() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 11);
    }


}

