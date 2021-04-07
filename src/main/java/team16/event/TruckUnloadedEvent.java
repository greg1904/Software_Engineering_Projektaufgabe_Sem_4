package team16.event;

import team16.vehicle.AutonomousCar;
import team16.vehicle.Truck;

public class TruckUnloadedEvent implements IEvent {
    private final AutonomousCar car;
    private final Truck truck;

    public TruckUnloadedEvent(AutonomousCar car, Truck truck) {
        this.car = car;
        this.truck = truck;
    }

    public AutonomousCar getCar() {
        return car;
    }

    public Truck getTruck() {
        return truck;
    }
}
