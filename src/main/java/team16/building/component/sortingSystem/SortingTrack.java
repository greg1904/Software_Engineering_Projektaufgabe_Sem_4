package team16.building.component.sortingSystem;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import team16.building.PackageSortingCenter;
import team16.event.ProcessSortingTracksEvent;
import team16.storage.packet.Package;
import team16.storage.packet.PackageType;

import java.util.LinkedList;
import java.util.Queue;

public class SortingTrack { //SOLID-Prinzip: Chain of Responsibility
    private final PackageType type;
    private final PackageScanner packageScanner = new PackageScanner();
    private final Queue<Package> packages = new LinkedList<>();
    private final PackageSortingCenter center;
    private final SortingTrack successor;

    public SortingTrack(PackageType type, PackageSortingCenter center, SortingTrack successor) {
        this.type = type;
        center.register(this);
        this.center = center;
        this.successor = successor;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void receive(ProcessSortingTracksEvent event) {
        if (!packages.isEmpty()) {
            System.out.println("Starting to process all " + packages.size() + " " + type + "-Packages...");
            while (!packages.isEmpty()) {
                center.incrementPackages(packages.remove().getType());
            }
            System.out.println("All " + type + "-Packages done!");
        }
    }

    public boolean addPackage(Package packet) { //SOLID-Prinzip: Chain of Responsibility
        if(type != packet.getType()){
            if(successor == null){
                System.out.println("Last responsible SortingTrack has declined a package.");
                return false;
            }else{
                return successor.addPackage(packet);
            }
        }else if (packageScanner.checkPackage(packet)) {
            center.addForbiddenPackage(packet);
            System.out.println("Package " + packet.getId() + " has Explosive in it!");
        }
        return packages.add(packet);
    }
}
