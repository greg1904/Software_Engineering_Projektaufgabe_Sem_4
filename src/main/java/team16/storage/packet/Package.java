package team16.storage.packet;

import org.jetbrains.annotations.NotNull;
import team16.configuration.Configuration;
import team16.storage.StoragePool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Package {

    private final char[][][] content;
    private final int zipCode;
    private final String id;
    private final PackageType type;
    private final double weight;

    public Package() {
        id = StoragePool.INSTANCE.getId(Configuration.INSTANCE.packageIdLength);
        content = new char[Configuration.INSTANCE.packageContentSize[0]][Configuration.INSTANCE.packageContentSize[1]][Configuration.INSTANCE.packageContentSize[2]];
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                for (int k = 0; k < content[i][j].length; k++) {
                    content[i][j][k] = StoragePool.INSTANCE.contentPool[ThreadLocalRandom.current().nextInt(StoragePool.INSTANCE.contentPool.length)];
                }
            }
        }
        zipCode = ThreadLocalRandom.current().nextInt(Configuration.INSTANCE.zipCodeMaxExclusive - Configuration.INSTANCE.zipCodeMinInclusive) + Configuration.INSTANCE.zipCodeMinInclusive;
        int randomType = ThreadLocalRandom.current().nextInt(100);
        type = randomType < 80 ? PackageType.NORMAL : (randomType < 95 ? PackageType.EXPRESS : PackageType.VALUE);
        weight = ((int) (ThreadLocalRandom.current().nextDouble(Configuration.INSTANCE.packageMinWeightInclusive, Configuration.INSTANCE.packageMaxWeightExclusive) * 100.0)) / 100.0;
    }

    public Package(String id, String content, int zipCode, PackageType type, double weight) {
        this.id = id;
        this.zipCode = zipCode;
        this.type = type;
        this.weight = weight;
        this.content = fillCustomContent(content);
    }

    private static String[] splitEqually(String text, int size) {
        return IntStream.iterate(0, start -> start < text.length(), start -> start + size)
                .mapToObj(start -> text.substring(start, Math.min(text.length(), start + size)))
                .collect(Collectors.toCollection(() -> new ArrayList<>((text.length() + size - 1) / size)))
                .toArray(new String[]{});
    }

    public char[][][] getContent() {
        return content;
    }

    private char[][][] fillCustomContent(String cont) {
        char[][][] fin = new char[Configuration.INSTANCE.packageContentSize[0]][Configuration.INSTANCE.packageContentSize[1]][Configuration.INSTANCE.packageContentSize[2]];
        String[] contentSplit = splitEqually(cont, Configuration.INSTANCE.packageContentSize[2]);
        int count = 0;
        for (int i = 0; i < fin.length; i++) {
            for (int j = 0; j < fin[i].length; j++) {
                fin[i][j] = contentSplit[count++].toCharArray();
            }
        }
        return fin;
    }

    @NotNull
    public String getContentAsString() {
        return Arrays.stream(getContent())
                .flatMap(Arrays::stream)
                .map(String::new)
                .collect(Collectors.joining());
    }

    public void addContent(String cont) {
        Random r = new Random();
        int i = r.nextInt(content.length);
        int j = r.nextInt(content[i].length);
        int k = r.nextInt(content[i][j].length - cont.length());
        System.arraycopy(cont.toCharArray(), 0, content[i][j], k, cont.length());
    }

    public int getZipCode() {
        return zipCode;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public PackageType getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }
}
