package team16.base;

import team16.employees.security.encryption.AESStrategy;
import team16.employees.security.encryption.IEncryptionStrategy;
import team16.location.sorting.utils.SearchAlgorithm;

public enum Configuration {
    instance;

    public final String encryptionKey = "dhbw";
    public final String homeDir = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String dataDir = homeDir + fileSeparator + "data";

    public final String packageCSVData = dataDir + fileSeparator + "base_package.csv";
    public final String boxCSVData = dataDir + fileSeparator + "base_box.csv";
    public final String palletCSVData = dataDir + fileSeparator + "base_pallet.csv";
    public final String truckCSVData = dataDir + fileSeparator + "base_truck.csv";
    public final String reportData = dataDir + fileSeparator + "report.txt";

    public final String boyerMooreJarPath = homeDir + fileSeparator + "components" + fileSeparator
            + "BoyerMoore" + fileSeparator + "build" + fileSeparator + "libs" + fileSeparator + "BoyerMoore.jar";
    public final String rabinKarpJarPath = homeDir + fileSeparator + "components" + fileSeparator
            + "RabinKarp" + fileSeparator + "build" + fileSeparator + "libs" + fileSeparator + "RabinKarp.jar";
    public final int[] packageContentSizes = {25, 10, 10};
    public final int packageCount = 24_000;
    public final int boxesCount = 600;
    public final int palletCount = 50;
    public final int truckCount = 5;
    public IEncryptionStrategy currentEncryptionStrategy = new AESStrategy();
    public String searchAlgorithmJarPath = rabinKarpJarPath;
    public SearchAlgorithm searchAlgorithm = new SearchAlgorithm(searchAlgorithmJarPath);
}
