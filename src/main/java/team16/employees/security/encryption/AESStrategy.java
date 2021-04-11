package team16.employees.security.encryption;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESStrategy implements IEncryptionStrategy{
    @Override
    public String decrypt(String cypher, String key) { //SOLID-Prinzip: Strategy
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, EncryptionStrategyContext.generate("AES", key));
            return new String(cipher.doFinal(Base64.getDecoder().decode(cypher)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String encrypt(String message, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, EncryptionStrategyContext.generate("AES", key));
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
