package team16.vehicle;

import team16.storage.pallet.Pallet;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("UnusedReturnValue")
public class Truck {

    private final static char[] idPool;

    static {
        StringBuilder builder = new StringBuilder();
        for (char c = 'A'; c < 'Z' + 1; c++) {
            builder.append(c);
        }
        for (char c = '0'; c < '9' + 1; c++) {
            builder.append(c);
        }
        idPool = builder.toString().toCharArray();
    }

    private final String id;
    private final Trailer trailer = new Trailer();

    public Truck() {
        this(IntStream.range(0, 4)
                .mapToObj(i -> String.valueOf(idPool[new Random().nextInt(idPool.length)]))
                .collect(Collectors.joining()));
    }

    public Truck(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean addPallet(Pallet pallet) {
        return trailer.addPallet(pallet);
    }

    public boolean addPalletLeft(Pallet pallet, int pos) {
        return trailer.addPalletLeft(pallet, pos);
    }

    public boolean addPalletRight(Pallet pallet, int pos) {
        return trailer.addPalletRight(pallet, pos);
    }

    public int[] getPosition(Pallet pallet) {
        return trailer.getPosition(pallet);
    }

    public boolean hasRoom() {
        return trailer.hasRoom();
    }

    public boolean hasLoad() {
        return trailer.hasLoad();
    }

    public Pallet getNextPallet() {
        if (hasLoad()) {
            return trailer.getNextPallet();
        }
        return null;
    }
}
