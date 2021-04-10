package team16.base;

import team16.employees.security.encryption.EncryptionStrategy;
import team16.location.sorting.utils.SearchAlgorithm;

public enum Configuration {
    instance;

    public final String encryptionKey = "dhbw";
    public final EncryptionStrategy encryptionStrategy = EncryptionStrategy.AES;
    public final String homeDir = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String dataDir = homeDir + fileSeparator + "output";
    public final String packageOutput = dataDir + fileSeparator + "base_package.csv";
    public final String boxOutput = dataDir + fileSeparator + "base_box.csv";
    public final String palletOutput = dataDir + fileSeparator + "base_pallet.csv";
    public final String truckOutput = dataDir + fileSeparator + "base_truck.csv";
    public final String reportOutput = dataDir + fileSeparator + "report.txt";

    public final String boyerMooreJarPath = homeDir + fileSeparator + "components" + fileSeparator
            + "BoyerMoore" + fileSeparator + "build" + fileSeparator + "libs" + fileSeparator + "BoyerMoore.jar";
    public final String rabinKarpJarPath = homeDir + fileSeparator + "components" + fileSeparator
            + "RabinKarp" + fileSeparator + "build" + fileSeparator + "libs" + fileSeparator + "RabinKarp.jar";

    public String searchAlgorithmJarPath = rabinKarpJarPath;

    public final int packageTrackNum = 8;
    public final int packageTrackSize = 600;
    public final int sortingTrackNum = 3;
    public final int autonomousCarNum = 5;
    public final int packageCount = 24_000;
    public final int packageIdLength = 6;
    public final double packageMinWeightInclusive = 1.00;
    public final double packageMaxWeightExclusive = 5.01;
    public final int[] packageContentSize = {25, 10, 10};
    public final int zipCodeMaxExclusive = 99_999;
    public final int zipCodeMinInclusive = 1067;
    public final int boxesCount = 600;
    public final int boxLayerCount = 5;
    public final int boxLayerSize = 4;
    public final int boxIdLength = 5;
    public final int palletCount = 50;
    public final int[] palletPositions = {2, 2};
    public final int truckCount = 5;
    public final int truckIdLength = 4;
    public final int trailerPalletCount = 5;
    public final int interimPositionsMax = 5;
    public final int interimPositionsMaxRoom = 2;
    public final int parkingZoneAutoCarCount = 5;
    public final int unloadZoneNum = 7;
    public final int maxWrongPinCount = 3;
    public final int maxWrongSuperPinCount = 2;

    public SearchAlgorithm searchAlgorithm = new SearchAlgorithm(searchAlgorithmJarPath);
}
