package team16.location.sorting.sensors;

import team16.communication.events.PackageTrackFullEvent;
import team16.location.PackageSortingCenter;
import team16.location.sorting.structure.PackageTrack;

public class PackageTrackFillSensor { //SOLID-Prinzip: Observer
    private final PackageTrack track;
    private final PackageSortingCenter center;

    public PackageTrackFillSensor(PackageTrack track, PackageSortingCenter center) {
        this.track = track;
        this.center = center;
    }

    public void checkFillingStatus() {
        if (track.isFull()) {
            System.out.println("Packagetrack " + track + "is full!");
            center.postEvent(new PackageTrackFullEvent(track));
        }
    }
}
