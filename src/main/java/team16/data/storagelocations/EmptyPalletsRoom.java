package team16.data.storagelocations;

import team16.data.datainstances.pallet.Pallet;

import java.util.Collection;
import java.util.List;

public class EmptyPalletsRoom extends StorageRoom<Pallet> {
    public void addPallet(Pallet pallet) {
        add(pallet);
    }

    public List<Pallet> getPallets() {
        return getStoredItems();
    }
}
