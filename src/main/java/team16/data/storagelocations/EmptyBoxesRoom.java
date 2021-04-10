package team16.data.storagelocations;

import team16.data.datainstances.box.Box;

import java.util.List;

public class EmptyBoxesRoom extends StorageRoom<Box> {
    public void addBox(Box box) {
        add(box);
    }

    public List<Box> getBoxes() {
        return getStoredItems();
    }
}
