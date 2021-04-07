package team16.building.component;

import java.util.EventListener;

public interface ITruckListener extends EventListener {//SOLID-Prinzip: Observer

    void truckArrived(int id);
}
