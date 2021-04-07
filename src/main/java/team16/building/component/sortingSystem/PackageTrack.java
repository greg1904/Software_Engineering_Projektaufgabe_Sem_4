package team16.building.component.sortingSystem;

import team16.building.PackageSortingCenter;
import team16.configuration.Configuration;
import team16.storage.packet.Package;

import java.util.LinkedList;
import java.util.Queue;

public class PackageTrack {

    private final Queue<Package> packages = new LinkedList<>();
    private final Sensor sensor;

    public PackageTrack(PackageSortingCenter center) {
        sensor = new Sensor(this, center);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addPackage(Package packet) {
        if (!isFull()) {
            boolean ret = packages.add(packet);
            notifySensor();
            return ret;
        }
        return false;
    }

    private void notifySensor() {
        sensor.checkFillingStatus();
    }

    public Package getNextPackage() {
        return packages.poll();
    }

    public boolean isEmpty() {
        return packages.isEmpty();
    }

    public boolean isFull() {
        return packages.size() >= Configuration.INSTANCE.packageTrackSize;
    }

    public boolean hasRoom() {
        return !isFull();
    }

}
