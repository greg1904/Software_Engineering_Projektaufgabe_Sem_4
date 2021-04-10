package team16.data.datainstances.pallet;

import team16.data.datainstances.box.Box;

public class PalletPosition {
    private final Box[] boxes = new Box[3];

    public boolean hasRoom() {
        return !isFilled();
    }

    public boolean isFilled() {
        for (Box b : boxes) {
            if (b == null)
                return false;
        }

        return true;
    }

    public boolean hasLoad() {
        for (Box b : boxes) {
            if (b != null)
                return true;
        }

        return false;
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
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i] == box)
                return i;
        }
        return -1;
    }
}
