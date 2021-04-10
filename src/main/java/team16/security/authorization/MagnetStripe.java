package team16.security.authorization;

import team16.configuration.Configuration;

public class MagnetStripe {
    private final String storedData;

    public MagnetStripe(String data) {
        String encrypted = Configuration.instance.encryptionStrategy.encrypt(data, Configuration.instance.encryptionKey);
        if (encrypted.length() <= 100) {
            this.storedData = encrypted;
        } else {
            this.storedData = encrypted.substring(0, 100);
        }
    }

    public String getStoredData() {
        return storedData;
    }
}
