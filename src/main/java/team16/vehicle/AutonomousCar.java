package team16.vehicle;

import team16.building.PackageSortingCenter;
import team16.building.UnloadZone;
import team16.event.TruckUnloadedEvent;

public class AutonomousCar {

    private final PackageSortingCenter center;

    public AutonomousCar(PackageSortingCenter center) {
        this.center = center;
    }

    public void unloadZone(UnloadZone zone) {
        if (zone != null) {
            System.out.println("Truck in Zone " + zone + " is unloading...");
            Truck truck = zone.getTruck();
            while (truck.hasLoad()) {
                center.getInterimPalletStorage().addPallet(truck.getNextPallet());
            }
            System.out.println("Truck unloaded");
            center.incrementTrucksDone();
            center.pushEvent(new TruckUnloadedEvent(this, truck));
        }
    }
}
