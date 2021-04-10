package team16.location.sorting;

import com.google.common.eventbus.Subscribe;
import team16.base.Configuration;
import team16.communication.events.ProcessSortingTracksEvent;
import team16.communication.events.StartSortingEvent;
import team16.data.datainstances.packages.Package;
import team16.data.datainstances.packages.PackageType;
import team16.data.storagelocations.EmptyBoxesRoom;
import team16.data.storagelocations.EmptyPalletsRoom;
import team16.location.PackageSortingCenter;
import team16.location.sorting.structure.PackageTrack;
import team16.location.logistics.transportation.Robot;
import team16.location.sorting.structure.SortingTrack;

import java.util.Arrays;

public class SortingSystem {
    private final Robot robot = new Robot(this);
    private final EmptyBoxesRoom boxesRoom = new EmptyBoxesRoom();
    private final EmptyPalletsRoom palletsRoom = new EmptyPalletsRoom();
    private final PackageTrack[] packageTracks = new PackageTrack[Configuration.instance.packageTrackNum];
    private final SortingTrack[] sortingTracks = new SortingTrack[Configuration.instance.sortingTrackNum];
    private final PackageSortingCenter center;
    private boolean isLocked;

    public SortingSystem(PackageSortingCenter center) {
        Arrays.setAll(packageTracks, i -> new PackageTrack(center));

        for(int i=sortingTracks.length-1; i>=0; i--){
            if(i == sortingTracks.length-1){
                sortingTracks[i] = new SortingTrack(PackageType.values()[i], center, null);
            }else{
                sortingTracks[i] = new SortingTrack(PackageType.values()[i], center, sortingTracks[i+1]);
            }
        }
        this.center = center;
        center.register(this);
        center.register(robot);
    }

    public PackageSortingCenter getCenter() {
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
            System.out.println("Starting sorting process");

            for(PackageTrack track : packageTracks){
                while (!track.isEmpty()){
                    insertIntoSortingTracks(track.getNextPackage());
                }
            }

            center.postEvent(new ProcessSortingTracksEvent());
        }
    }

    private void insertIntoSortingTracks(Package packet) { //SOLID-Prinzip: Chain of Responsibility
        if (!isLocked) {
            sortingTracks[0].addPackage(packet);
//            switch (packet.getType()) {
//                case VALUE -> sortingTracks[2].addPackage(packet);
//                case EXPRESS -> sortingTracks[1].addPackage(packet);
//                case NORMAL -> sortingTracks[0].addPackage(packet);
//            }
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
