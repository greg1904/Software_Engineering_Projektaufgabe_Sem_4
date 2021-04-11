package team16.employees.security.encryption;

public interface IEncryptionStrategy { //SOLID-Prinzip: Strategy
    String decrypt(String cypher, String key);

    String encrypt(String message, String key);
}
