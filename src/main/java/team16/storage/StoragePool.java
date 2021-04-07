package team16.storage;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum StoragePool {
    INSTANCE;

    public final char[] idPool;
    public final char[] contentPool;
    private final Random rand = new Random();

    StoragePool() {
        StringBuilder builder = new StringBuilder();
        for (char c = 'a'; c < 'z' + 1; c++) {
            builder.append(c);
        }
        for (char c = '0'; c < '9' + 1; c++) {
            builder.append(c);
        }
        idPool = builder.toString().toCharArray();

        builder = new StringBuilder();
        for (char c = 'a'; c < 'z' + 1; c++) {
            builder.append(c);
        }
        builder.append(".:-!");
        contentPool = builder.toString().toCharArray();
    }

    public String getId(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> String.valueOf(idPool[rand.nextInt(idPool.length)]))
                .collect(Collectors.joining());
    }

}
