package team16.employees.security.idcard;

import team16.base.Configuration;

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
