package team16.building.component.sortingSystem;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import team16.building.PackageSortingCenter;
import team16.event.ProcessSortingTracksEvent;
import team16.storage.packet.Package;
import team16.storage.packet.PackageType;

import java.util.LinkedList;
import java.util.Queue;

public class SortingTrack {
    private final PackageType type;
    private final Scanner scanner = new Scanner();
    private final Queue<Package> packages = new LinkedList<>();
    private final PackageSortingCenter center;

    public SortingTrack(PackageType type, PackageSortingCenter center) {
        this.type = type;
        center.register(this);
        this.center = center;
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

    public boolean addPackage(Package packet) {
        if (scanner.checkPackage(packet)) {
            center.addForbiddenPackage(packet);
            System.out.println("Package " + packet.getId() + " has Explosive in it!");
        }
        return packages.add(packet);
    }
}
