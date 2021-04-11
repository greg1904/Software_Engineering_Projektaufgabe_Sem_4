package team16.employees.security.encryption;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class EncryptionStrategyContext { //SOLID-Prinzip: Strategy
    private final IEncryptionStrategy strategy;

    public EncryptionStrategyContext(IEncryptionStrategy strategy) {
        this.strategy = strategy;
    }

    static SecretKeySpec generate(String encryptionType, String inputKey) {
        MessageDigest sha;

        try {
            byte[] key = inputKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, encryptionType.equals("AES") ? 16 : 8);
            return new SecretKeySpec(key, encryptionType);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String decrypt(String cypher, String key) {
        return strategy.decrypt(cypher, key);
    }

    public String encrypt(String message, String key) {
        return strategy.encrypt(message, key);
    }

}
