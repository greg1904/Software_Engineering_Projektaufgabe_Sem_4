package team16.employees.security.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public enum EncryptionStrategy { //SOLID-Prinzip: Strategy
    AES, DES;

    public String decrypt(String cypher, String key) {
        try {
            Cipher cipher = Cipher.getInstance(name() + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, generate(key));
            return new String(cipher.doFinal(Base64.getDecoder().decode(cypher)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String encrypt(String message, String key) {
        try {
            Cipher cipher = Cipher.getInstance(name() + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, generate(key));
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private SecretKeySpec generate(String inputKey) {
        MessageDigest sha;

        try {
            byte[] key = inputKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, this == AES ? 16 : 8);
            return new SecretKeySpec(key, name());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
