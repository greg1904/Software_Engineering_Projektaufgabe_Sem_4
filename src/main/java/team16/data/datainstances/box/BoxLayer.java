package team16.data.datainstances.box;

import team16.data.datainstances.packages.Package;

public class BoxLayer {
    private final Package[][] packages = new Package[2][4];

    public boolean isFull() {
        for (int i = 0; i < packages.length; i++) {
            for (int j = 0; j < packages[i].length; j++) {
                if (packages[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for (int i = 0; i < packages.length; i++) {
            for (int j = 0; j < packages[i].length; j++) {
                if (packages[i][j] != null)
                    return false;
            }
        }
        return true;
    }

    public boolean addPackage(Package packet) {
        return addPackageLeft(packet) || addPackageRight(packet);
    }

    public boolean addPackageLeft(Package packet) {
        for (int i = 0; i < packages[0].length; i++) {
            if (packages[0][i] == null) {
                packages[0][i] = packet;
                return true;
            }
        }
        return false;
    }

    public boolean addPackageRight(Package packet) {
        for (int i = 0; i < packages[1].length; i++) {
            if (packages[1][i] == null) {
                packages[1][i] = packet;
                return true;
            }
        }
        return false;
    }

    public Package getNextPackage() {
        Package packet = getNextPackageFromLeft();
        return packet == null ? getNextPackageFromRight() : packet;
    }

    public Package getNextPackageFromLeft() {
        for (int i = 0; i < packages[0].length; i++) {
            if (packages[0][i] != null) {
                Package packet = packages[0][i];
                packages[0][i] = null;
                return packet;
            }
        }
        return null;
    }

    public Package getNextPackageFromRight() {
        for (int i = 0; i < packages[1].length; i++) {
            if (packages[1][i] != null) {
                Package packet = packages[1][i];
                packages[1][i] = null;
                return packet;
            }
        }
        return null;
    }
}
