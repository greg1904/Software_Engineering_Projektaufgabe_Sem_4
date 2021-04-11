package team16.location.sorting.structure;

import com.google.common.eventbus.Subscribe;
import team16.communication.events.ProcessSortingTracksEvent;
import team16.data.datainstances.packages.Package;
import team16.data.datainstances.packages.PackageType;
import team16.location.PackageSortingCenter;
import team16.location.sorting.sensors.PackageScanner;

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
    public void receive(ProcessSortingTracksEvent event) {
        if (!packages.isEmpty()) {
            System.out.println("Starting to process all " + packages.size() + " " + type + "-Packages...");
            while (!packages.isEmpty()) {
                center.incrementPackages(packages.remove().getType());
            }
            System.out.println("All " + type + "-Packages done!");
        }
        System.out.println();
    }

    public boolean addPackage(Package packet) { //SOLID-Prinzip: Chain of Responsibility
        if (type != packet.getType()) {
            if (successor == null) {
                System.out.println("Last responsible SortingTrack has declined a package.");
                return false;
            } else {
                return successor.addPackage(packet);
            }
        } else if (packageScanner.checkPackage(packet)) {
            center.addForbiddenPackage(packet);
            System.out.println("Package " + packet.getId() + " has Explosive in it!");
            return false;
        }
        return packages.add(packet);
    }
}
