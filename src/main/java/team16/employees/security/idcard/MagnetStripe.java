package team16.employees.security.idcard;

import team16.base.Configuration;
import team16.employees.security.encryption.EncryptionStrategyContext;

public class MagnetStripe {
    private final String storedData;

    public MagnetStripe(String data) {
        String encrypted = new EncryptionStrategyContext(Configuration.instance.currentEncryptionStrategy).encrypt(data, Configuration.instance.encryptionKey);
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
