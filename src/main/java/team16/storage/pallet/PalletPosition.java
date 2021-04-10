package team16.storage.pallet;

import team16.storage.box.Box;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class PalletPosition {
    private final Box[] boxes = new Box[3];

    public boolean hasRoom() {
        return !isFilled();
    }

    public boolean isFilled() {
        for(Box b:boxes){
            if(b == null)
                return false;
        }

        return true;
//        return Arrays.stream(boxes).noneMatch(Objects::isNull);
    }

    public boolean hasLoad() {
        for(Box b:boxes){
            if(b != null)
                return true;
        }

        return false;
//        return Arrays.stream(boxes).anyMatch(Objects::nonNull);
    }

    public boolean addBox(Box box) {
        if (!isFilled()) {
            for (int i = 0; i < boxes.length; i++) {
                if (boxes[i] == null) {
                    boxes[i] = box;
                    return true;
                }
            }
        }
        return false;
    }

    public Box[] getBoxes() {
        return boxes;
    }

    public boolean addBox(Box box, int layer) {
        if (boxes[layer] == null) {
            boxes[layer] = box;
            return true;
        }
        return false;
    }

    public Box getNextBox() {
        if (hasLoad()) {
            for (int i = boxes.length - 1; i >= 0; i--) {
                if (boxes[i] != null) {
                    Box box = boxes[i];
                    boxes[i] = null;
                    return box;
                }
            }
        }
        return null;
    }

    public boolean hasBox(Box box) {
        return getLayerIndex(box) != -1;
    }

    public int getLayerIndex(Box box) {
        for(int i=0; i<boxes.length; i++){
            if(boxes[i] == box)
                return i;
        }
        return -1;
//        return IntStream.range(0, boxes.length).filter(i -> boxes[i] == box).findFirst().orElse(-1);
    }
}
