package team16.configuration;

import team16.security.EncryptionStrategy;

public enum Configuration {
    instance;

    public final String key = "dhbw";
    public final EncryptionStrategy encryptionStrategy = EncryptionStrategy.AES;
    public final String homeDir = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String outputDir = homeDir + fileSeparator + "output";
    public final String packageOutput = outputDir + fileSeparator + "base_package.csv";
    public final String boxOutput = outputDir + fileSeparator + "base_box.csv";
    public final String palletOutput = outputDir + fileSeparator + "base_pallet.csv";
    public final String truckOutput = outputDir + fileSeparator + "base_truck.csv";
    public final String reportOutput = outputDir + fileSeparator + "report.txt";

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
    public final int parkingZoneTruckCount = 5;
    public final int unloadZoneNum = 7;
    public final int maxWrongPinCount = 3;
    public final int maxWrongSuperPinCount = 2;
    public SearchAlgorithm searchAlgorithm = SearchAlgorithm.RABINKARP;
}
