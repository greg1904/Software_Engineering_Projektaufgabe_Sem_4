package team16.location.logistics.transportation;

import team16.location.PackageSortingCenter;
import team16.data.transport.Truck;
import team16.communication.events.TruckUnloadedEvent;
import team16.location.logistics.zones.UnloadZone;

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
