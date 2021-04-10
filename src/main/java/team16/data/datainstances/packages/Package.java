package team16.data.datainstances.packages;

import team16.base.Configuration;
import team16.data.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;
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
        id = IdGenerator.getId(6);
        content = new char[Configuration.instance.packageContentSizes[0]][Configuration.instance.packageContentSizes[1]][Configuration.instance.packageContentSizes[2]];
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                for (int k = 0; k < content[i][j].length; k++) {
                    content[i][j][k] = IdGenerator.CONTENT_POOL[ThreadLocalRandom.current().nextInt(IdGenerator.CONTENT_POOL.length)];
                }
            }
        }
        zipCode = ThreadLocalRandom.current().nextInt(99_999 - 1067) + 1067;
        int randomType = ThreadLocalRandom.current().nextInt(100);
        type = randomType < 80 ? PackageType.NORMAL : (randomType < 95 ? PackageType.EXPRESS : PackageType.VALUE);
        weight = ((int) (ThreadLocalRandom.current().nextDouble(1.00, 5.01) * 100.0)) / 100.0;
    }

    public Package(String id, String content, int zipCode, PackageType type, double weight) {
        this.id = id;
        this.zipCode = zipCode;
        this.type = type;
        this.weight = weight;
        this.content = getContentFromString(content);
    }

    private static List<String> splitEqually(String text, int size) {
        List<String> parts = new ArrayList<>();
        int startIndex = 0;
        while(startIndex + size <= text.length()){
            parts.add(text.substring(startIndex, startIndex+size));
            startIndex += size;
        }
        return parts;
    }

    private char[][][] getContentFromString(String cont) {
        char[][][] fin = new char[Configuration.instance.packageContentSizes[0]][Configuration.instance.packageContentSizes[1]][Configuration.instance.packageContentSizes[2]];
        List<String> contentSplit = splitEqually(cont, Configuration.instance.packageContentSizes[2]);
        int count = 0;
        for (int i = 0; i < fin.length; i++) {
            for (int j = 0; j < fin[i].length; j++) {
                fin[i][j] = contentSplit.get(count++).toCharArray();
            }
        }
        return fin;
    }


    public String getContentAsString() {
        StringBuilder contentBuilder = new StringBuilder();

        for (int l = 0; l < content.length; l++) {
            for (int w = 0; w < content[l].length; w++) {
                for (int h = 0; h < content[l][w].length; h++) {
                    contentBuilder.append(content[l][w][h]);
                }
            }
        }

        return contentBuilder.toString();
    }

    public void addContent(String cont) {
        Random rand = new Random();
        int l = rand.nextInt(content.length);
        int w = rand.nextInt(content[l].length);
        int h = rand.nextInt(content[l][w].length - cont.length());
        System.arraycopy(cont.toCharArray(), 0, content[l][w], h, cont.length());
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getId() {
        return id;
    }

    public PackageType getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }
}
