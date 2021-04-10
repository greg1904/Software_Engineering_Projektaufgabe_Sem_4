package team16.vehicle;

import team16.building.PackageSortingCenter;
import team16.building.UnloadZone;
import team16.event.TruckUnloadedEvent;

public class AutonomousCar {
    private final PackageSortingCenter packageSortingCenter;

    public AutonomousCar(PackageSortingCenter packageSortingCenter) {
        this.packageSortingCenter = packageSortingCenter;
    }

    public void unloadZone(UnloadZone zone) {
        if (zone != null) {
            System.out.println("Truck in UnloadingZone " + zone + " is being unloaded.");
            Truck truck = zone.getTruck();
            while (truck.hasLoad()) {
                packageSortingCenter.getTemporaryPalletStorage().addPallet(truck.getNextPallet());
            }
            System.out.println("Truck has been unloaded!");
            packageSortingCenter.incrementTrucksDone();
            packageSortingCenter.postEvent(new TruckUnloadedEvent(this, truck));
        }
    }
}
