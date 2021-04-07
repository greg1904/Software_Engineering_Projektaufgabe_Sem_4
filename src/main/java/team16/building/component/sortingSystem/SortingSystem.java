package team16.building.component.sortingSystem;

import com.google.common.eventbus.Subscribe;
import team16.building.PackageSortingCenter;
import team16.configuration.Configuration;
import team16.event.ProcessSortingTracksEvent;
import team16.event.StartSortingEvent;
import team16.storage.packet.Package;
import team16.storage.packet.PackageType;
import team16.storage.room.trash.EmptyBoxesRoom;
import team16.storage.room.trash.EmptyPalletsRoom;

import java.util.Arrays;

@SuppressWarnings({"UnstableApiUsage", "unused", "FieldCanBeLocal"})
public class SortingSystem {

    private final Robot robot = new Robot(this);
    private final EmptyBoxesRoom boxesRoom = new EmptyBoxesRoom();
    private final EmptyPalletsRoom palletsRoom = new EmptyPalletsRoom();
    private final PackageTrack[] packageTracks = new PackageTrack[Configuration.INSTANCE.packageTrackNum];
    private final SortingTrack[] sortingTracks = new SortingTrack[Configuration.INSTANCE.sortingTrackNum];
    private final PackageSortingCenter center;
    private boolean isLocked;

    public SortingSystem(PackageSortingCenter center) {
        Arrays.setAll(packageTracks, i -> new PackageTrack(center));
        Arrays.setAll(sortingTracks, i -> new SortingTrack(PackageType.values()[i], center));
        this.center = center;
        center.register(this);
        center.register(robot);
    }

    PackageSortingCenter getCenter() {
        return center;
    }

    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
    }

    @Subscribe
    public void receive(StartSortingEvent event) {
        if (!isLocked) {
            System.out.println("Start sorting process");
            Arrays.stream(packageTracks).forEach(track -> {
                while (!track.isEmpty()) {
                    insertIntoSortingTrack(track.getNextPackage());
                }
            });
            center.pushEvent(new ProcessSortingTracksEvent());
        }
    }

    private void insertIntoSortingTrack(Package packet) {//SOLID-Prinzip: Chain of Responsibility
        if (!isLocked) {
            switch (packet.getType()) {
                case VALUE -> sortingTracks[2].addPackage(packet);
                case EXPRESS -> sortingTracks[1].addPackage(packet);
                case NORMAL -> sortingTracks[0].addPackage(packet);
            }
        }
    }

    public EmptyBoxesRoom getEmptyBoxesRoom() {
        return boxesRoom;
    }

    public EmptyPalletsRoom getEmptyPalletsRoom() {
        return palletsRoom;
    }

    public PackageTrack[] getPackageTracks() {
        return packageTracks;
    }

    public SortingTrack[] getSortingTracks() {
        return sortingTracks;
    }
}
