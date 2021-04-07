package team16.storage.room.trash;

import team16.storage.box.Box;

import java.util.ArrayList;
import java.util.List;

public class EmptyBoxesRoom implements IStorageRoom {

    private final List<Box> boxes = new ArrayList<>();

    public void addBox(Box box) {
        boxes.add(box);
    }

    @SuppressWarnings("unused")
    public void removeBox(Box box) {
        boxes.remove(box);
    }

    public List<Box> getBoxes() {
        return boxes;
    }
}
