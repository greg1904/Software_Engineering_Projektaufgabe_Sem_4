package team16.location.sorting.structure;

import team16.data.datainstances.packages.Package;
import team16.location.PackageSortingCenter;
import team16.location.sorting.sensors.PackageTrackFillSensor;

import java.util.LinkedList;
import java.util.Queue;

public class PackageTrack {
    private final Queue<Package> packages = new LinkedList<>();
    private final PackageTrackFillSensor packageTrackFillSensor;

    public PackageTrack(PackageSortingCenter center) {
        packageTrackFillSensor = new PackageTrackFillSensor(this, center);
    }

    public boolean addPackage(Package packet) {
        if (!isFull()) {
            boolean ret = packages.add(packet);
            checkFillStatus();
            return ret;
        }
        return false;
    }

    private void checkFillStatus() {
        packageTrackFillSensor.checkFillingStatus();
    }

    public Package getNextPackage() {
        return packages.poll();
    }

    public boolean isEmpty() {
        return packages.isEmpty();
    }

    public boolean isFull() {
        return packages.size() >= 600;
    }

    public boolean hasRoom() {
        return !isFull();
    }

}
