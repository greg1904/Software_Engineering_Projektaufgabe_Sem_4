package team16.building.component.sortingSystem;

import team16.building.PackageSortingCenter;
import team16.event.PackageTrackFullEvent;

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
