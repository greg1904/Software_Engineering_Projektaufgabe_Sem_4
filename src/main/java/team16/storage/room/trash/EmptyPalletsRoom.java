package team16.storage.room.trash;

import team16.storage.pallet.Pallet;

import java.util.ArrayList;
import java.util.List;

public class EmptyPalletsRoom implements IStorageRoom {

    private final List<Pallet> boxes = new ArrayList<>();

    public void addPallet(Pallet pallet) {
        boxes.add(pallet);
    }

    @SuppressWarnings("unused")
    public void removePallet(Pallet pallet) {
        boxes.remove(pallet);
    }

    public List<Pallet> getPallets() {
        return boxes;
    }
}
