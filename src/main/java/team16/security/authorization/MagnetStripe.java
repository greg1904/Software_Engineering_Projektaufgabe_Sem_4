package team16.security.authorization;

import team16.configuration.Configuration;

public class MagnetStripe {

    private final String content;

    public MagnetStripe(String content) {
        String encrypted = Configuration.instance.encryptionStrategy.encrypt(content, Configuration.instance.key);
        if (encrypted.length() <= 100) {
            this.content = encrypted;
        } else {
            this.content = encrypted.substring(0, 100);
        }
    }

    public String getContent() {
        return content;
    }
}
