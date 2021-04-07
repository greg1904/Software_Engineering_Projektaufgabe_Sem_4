package team16.event;

import team16.building.component.sortingSystem.PackageTrack;

public class PackageTrackFullEvent implements IEvent {

    private final PackageTrack track;

    public PackageTrackFullEvent(PackageTrack track) {
        this.track = track;
    }

    public PackageTrack getTrack() {
        return track;
    }
}
