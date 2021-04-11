package team16.data.transport;

import team16.data.datainstances.pallet.Pallet;
import team16.data.utils.IdGenerator;

public class Truck {
    private final String id;
    private final Trailer trailer = new Trailer();

    public Truck() {
        this.id = IdGenerator.getId(4);
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

    public boolean addPallet(Pallet pallet, int pos, boolean isLeft) {
        if (isLeft)
            return trailer.addPalletLeft(pallet, pos);
        else
            return trailer.addPalletRight(pallet, pos);
    }

    public int[] getPosition(Pallet pallet) {
        return trailer.getPosition(pallet);
    }

    public boolean hasRoom() {
        return !trailer.isFull();
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
