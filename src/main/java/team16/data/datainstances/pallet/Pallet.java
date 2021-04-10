package team16.data.datainstances.pallet;

import team16.base.Configuration;
import team16.data.datainstances.box.Box;

public class Pallet {
    private static int nextUsableId = 1;
    private final int id;
    private final PalletPosition[][] palletPositions = new PalletPosition[2][2];

    public Pallet() {
        this(nextUsableId++);
    }

    public Pallet(int id) {
        for (int i = 0; i < palletPositions.length; i++) {
            for (int j = 0; j < palletPositions[i].length; j++) {
                palletPositions[i][j] = new PalletPosition();
            }
        }

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPositionIndex(Box box) {
        for (int i = 0; i < palletPositions.length; i++) {
            for (int j = 0; j < palletPositions[i].length; j++) {
                if (palletPositions[i][j].hasBox(box))
                    return i + j * 2;
            }
        }

        return -1;
    }

    public int getLayerIndex(Box box) {
        int position = getPositionIndex(box);
        return palletPositions[position % 2][position >= 2 ? 1 : 0].getLayerIndex(box);
    }

    public boolean addBox(Box box) {
        if (hasRoom()) {
            for (int i = 0; i < palletPositions.length; i++) {
                for (int j = 0; j < palletPositions[i].length; j++) {
                    if (palletPositions[i][j].hasRoom()) {
                        palletPositions[i][j].addBox(box);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean addBox(Box box, int position, int layer) {
        if (hasRoom()) {
            PalletPosition pos = palletPositions[position % 2][position >= 2 ? 1 : 0];
            if (pos == null) {
                return false;
            } else {
                return pos.addBox(box, layer);
            }
        }
        return false;
    }

    public Box getNextBox() {
        for (PalletPosition[] palletPositions : this.palletPositions) {
            for (PalletPosition palletPosition : palletPositions) {
                Box box = palletPosition.getNextBox();
                if (box != null) {
                    return box;
                }
            }
        }
        return null;
    }

    public boolean hasLoad() {
        for (int i = 0; i < palletPositions.length; i++) {
            for (int j = 0; j < palletPositions[i].length; j++) {
                if (palletPositions[i][j].hasLoad())
                    return true;
            }
        }
        return false;
    }

    public boolean hasRoom() {
        for (int i = 0; i < palletPositions.length; i++) {
            for (int j = 0; j < palletPositions[i].length; j++) {
                if (palletPositions[i][j].hasRoom())
                    return true;
            }
        }
        return false;
    }
}
