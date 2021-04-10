package team16.communication.events;

import team16.location.sorting.structure.PackageTrack;

public class PackageTrackFullEvent implements IEvent {
    private final PackageTrack track;

    public PackageTrackFullEvent(PackageTrack track) {
        this.track = track;
    }

    public PackageTrack getTrack() {
        return track;
    }
}
