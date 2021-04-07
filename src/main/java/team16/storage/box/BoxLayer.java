package team16.storage.box;

import team16.configuration.Configuration;
import team16.storage.packet.Package;

import java.util.Arrays;
import java.util.Objects;

public class BoxLayer {

    private final Package[][] packages = new Package[2][Configuration.INSTANCE.boxLayerSize];

    public BoxLayer() {
    }

    @SuppressWarnings("unused")
    public Package[] getLeftPackages() {
        return packages[0];
    }

    @SuppressWarnings("unused")
    public Package[] getRightPackages() {
        return packages[1];
    }

    public boolean isFull() {
        return Arrays.stream(packages).flatMap(Arrays::stream).noneMatch(Objects::isNull);
    }

    public boolean hasRoom() {
        return !isFull();
    }

    public boolean isEmpty() {
        return Arrays.stream(packages).flatMap(Arrays::stream).noneMatch(Objects::nonNull);
    }

    public boolean hasLoad() {
        return !isEmpty();
    }

    @SuppressWarnings("UnusedReturnValue")
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
