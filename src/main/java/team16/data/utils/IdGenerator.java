package team16.data.utils;

import java.util.Random;

public class IdGenerator {
    public static final char[] ID_POOL;
    public static final char[] CONTENT_POOL;

    static {
        StringBuilder builder = new StringBuilder();

        for (char c = 'a'; c <= 'z'; c++) {
            builder.append(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            builder.append(c);
        }

        ID_POOL = builder.toString().toCharArray();

        builder = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) {
            builder.append(c);
        }
        builder.append(".:-!");
        CONTENT_POOL = builder.toString().toCharArray();
    }

    public static String getId(int count) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i< count; i++){
            builder.append(ID_POOL[random.nextInt(ID_POOL.length)]);
        }

        return builder.toString();
    }

}
