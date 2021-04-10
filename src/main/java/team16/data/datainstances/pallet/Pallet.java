package team16.data.datainstances.pallet;

import team16.base.Configuration;
import team16.data.datainstances.box.Box;

public class Pallet {
    private static int nextUsableId = 1;
    private final int id;
    private final PalletPosition[][] palletPositions = new PalletPosition[Configuration.instance.palletPositions[0]][Configuration.instance.palletPositions[1]];

    public Pallet() {
        this(nextUsableId++);
    }

    public Pallet(int id) {
        for(int i = 0; i< palletPositions.length; i++){
            for(int j = 0; j< palletPositions[i].length; j++){
                palletPositions[i][j] = new PalletPosition();
            }
        }

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPositionIndex(Box box) {
        for(int i = 0; i< palletPositions.length; i++) {
            for (int j = 0; j < palletPositions[i].length; j++) {
                if(palletPositions[i][j].hasBox(box))
                    return i + j*2;
            }
        }

        return -1;
    }

    public int getLayerIndex(Box box) {
        return switch (getPositionIndex(box)) {
            case 0 -> palletPositions[0][0].getLayerIndex(box);
            case 1 -> palletPositions[1][0].getLayerIndex(box);
            case 2 -> palletPositions[0][1].getLayerIndex(box);
            case 3 -> palletPositions[1][1].getLayerIndex(box);
            default -> -1;
        };
    }

    public boolean addBox(Box box) {
        if (hasRoom()) {
            for(int i = 0; i< palletPositions.length; i++) {
                for (int j = 0; j < palletPositions[i].length; j++) {
                    if(palletPositions[i][j].hasRoom()) {
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
            PalletPosition pos = switch (position) {
                case 0 -> palletPositions[0][0];
                case 1 -> palletPositions[1][0];
                case 2 -> palletPositions[0][1];
                case 3 -> palletPositions[1][1];
                default -> null;
            };
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

    public PalletPosition[][] getPositions() {
        return palletPositions;
    }

    public PalletPosition[] getRightPositions() {
        return palletPositions[1];
    }

    public PalletPosition[] getLeftPositions() {
        return palletPositions[0];
    }

    public boolean hasLoad() {
        for(int i = 0; i< palletPositions.length; i++) {
            for (int j = 0; j < palletPositions[i].length; j++) {
                if(palletPositions[i][j].hasLoad())
                    return true;
            }
        }
        return false;
//        return Arrays.stream(positions).flatMap(Arrays::stream).anyMatch(Position::hasLoad);
    }

    public boolean hasRoom() {
        for(int i = 0; i< palletPositions.length; i++) {
            for (int j = 0; j < palletPositions[i].length; j++) {
                if(palletPositions[i][j].hasRoom())
                    return true;
            }
        }
        return false;
//        return Arrays.stream(positions).flatMap(Arrays::stream).anyMatch(Position::hasRoom);
    }
}
