package team16.building.component.sortingSystem;

import team16.building.PackageSortingCenter;
import team16.configuration.Configuration;
import team16.storage.packet.Package;

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
        return packages.size() >= Configuration.instance.packageTrackSize;
    }

    public boolean hasRoom() {
        return !isFull();
    }

}
