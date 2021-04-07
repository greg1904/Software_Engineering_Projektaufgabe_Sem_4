package team16.building.component.sortingSystem;

import team16.building.PackageSortingCenter;
import team16.event.PackageTrackFullEvent;

public class Sensor {//SOLID-Prinzip: Observer

    private final PackageTrack track;
    private final PackageSortingCenter center;

    public Sensor(PackageTrack track, PackageSortingCenter center) {
        this.track = track;
        this.center = center;
    }

    public void checkFillingStatus() {
        if (track.isFull()) {
            System.out.println("Packagetrack " + track + " full!");
            center.pushEvent(new PackageTrackFullEvent(track));
        }
    }
}
