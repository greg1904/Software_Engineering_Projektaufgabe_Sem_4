package team16.communication.events;

import team16.data.transport.Truck;
import team16.location.logistics.transportation.AutonomousCar;

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
