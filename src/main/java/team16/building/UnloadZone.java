package team16.building;

import com.google.common.eventbus.Subscribe;
import team16.building.component.ITruckListener;
import team16.building.component.TruckArrivalSensor;
import team16.event.TruckUnloadedEvent;
import team16.vehicle.Truck;

public class UnloadZone implements IZone {
    private static int currentId = 1;
    private final TruckArrivalSensor truckArrivalSensor;
    private final int id = currentId++;
    private Truck truck;

    public UnloadZone(ITruckListener listener) {
        this.truckArrivalSensor = new TruckArrivalSensor();
        truckArrivalSensor.addListener(listener);
    }

    public boolean addTruck(Truck truck) {
        if (truck == null) {
            System.out.println("No Truck available!");
            return false;
        }
        if (this.truck == null) {
            this.truck = truck;
            System.out.println("Truck moved to UnloadZone");
            truckArrivalSensor.notifyListeners(id);
            return true;
        }
        System.out.println("UnloadZone is already in use!");
        return false;
    }

    @Subscribe
    public void receive(TruckUnloadedEvent event) {
        if (event.getTruck() == truck) {
            System.out.println("Removing Truck from zone " + this + "!");
            truck = null;
        }
    }

    public Truck getTruck() {
        return truck;
    }

    public int getId() {
        return id;
    }

    public TruckArrivalSensor getSensor() {
        return truckArrivalSensor;
    }
}
