package team16.storage.pallet;

import team16.configuration.Configuration;
import team16.storage.box.Box;

import java.util.Arrays;

public class Pallet {

    private static int currentId = 1;
    private final int id;
    private final Position[][] positions = new Position[Configuration.INSTANCE.palletPositions[0]][Configuration.INSTANCE.palletPositions[1]];

    public Pallet() {
        this(currentId++);
    }

    public Pallet(int id) {
        Arrays.setAll(positions[0], j -> new Position());
        Arrays.setAll(positions[1], j -> new Position());
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPositionIndex(Box box) {
        if (positions[0][0].hasBox(box)) {
            return 0;
        } else if (positions[1][0].hasBox(box)) {
            return 1;
        } else if (positions[0][1].hasBox(box)) {
            return 2;
        } else if (positions[1][1].hasBox(box)) {
            return 3;
        }
        return -1;
    }

    public int getLayerIndex(Box box) {
        return switch (getPositionIndex(box)) {
            case 0 -> positions[0][0].getLayerIndex(box);
            case 1 -> positions[1][0].getLayerIndex(box);
            case 2 -> positions[0][1].getLayerIndex(box);
            case 3 -> positions[1][1].getLayerIndex(box);
            default -> -1;
        };
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addBox(Box box) {
        if (hasRoom()) {
            return Arrays.stream(positions).flatMap(Arrays::stream).filter(Position::hasRoom).findFirst().orElseThrow().addBox(box);
        }
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addBox(Box box, int position, int layer) {
        if (hasRoom()) {
            Position pos = switch (position) {
                case 0 -> positions[0][0];
                case 1 -> positions[1][0];
                case 2 -> positions[0][1];
                case 3 -> positions[1][1];
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
        for (Position[] positions : positions) {
            for (Position position : positions) {
                Box box = position.getNextBox();
                if (box != null) {
                    return box;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    public Position[][] getPositions() {
        return positions;
    }

    @SuppressWarnings("unused")
    public Position[] getRightPositions() {
        return positions[1];
    }

    @SuppressWarnings("unused")
    public Position[] getLeftPositions() {
        return positions[0];
    }

    public boolean hasLoad() {
        return Arrays.stream(positions).flatMap(Arrays::stream).anyMatch(Position::hasLoad);
    }

    public boolean hasRoom() {
        return Arrays.stream(positions).flatMap(Arrays::stream).anyMatch(Position::hasRoom);
    }
}
