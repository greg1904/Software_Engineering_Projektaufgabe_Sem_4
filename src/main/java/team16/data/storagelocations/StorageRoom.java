package team16.data.storagelocations;

import java.util.ArrayList;
import java.util.List;

public abstract class StorageRoom<E> {
    protected List<E> storedItems = new ArrayList<>();

    public void add(E item) {
        storedItems.add(item);
    }

    public void remove(E item) {
        storedItems.remove(item);
    }

    public List<E> getStoredItems() {
        return storedItems;
    }
}
