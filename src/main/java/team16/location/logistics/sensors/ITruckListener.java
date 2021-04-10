package team16.location.logistics.sensors;

import java.util.EventListener;

public interface ITruckListener extends EventListener {//SOLID-Prinzip: Observer

    void truckArrived(int id);
}
