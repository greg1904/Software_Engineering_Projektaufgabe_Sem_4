package team16.data.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IdGenerator {
    public static final Map<String, Boolean> givenIds = new HashMap<>();
    public static final Map<Integer, Boolean> givenNumericalIds = new HashMap<>();
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
        StringBuilder builder;

        do {
            builder = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < count; i++) {
                builder.append(ID_POOL[random.nextInt(ID_POOL.length)]);
            }
        }while (givenIds.containsKey(builder.toString()));
        givenIds.put(builder.toString(), true);
        return builder.toString();
    }

    public static int getNumericalId(int count){
        StringBuilder builder;

        do{
            Random random = new Random();
            builder = new StringBuilder();

            for(int i=0; i<count; i++){
                builder.append(random.nextInt(10));
            }
        }while (givenNumericalIds.containsKey(builder.toString()));
        int key = Integer.parseInt(builder.toString());
        givenNumericalIds.put(key, true);
        return key;
    }

}
