package team16.location.logistics.sensors;

import team16.location.logistics.sensors.ITruckListener;

public interface IListenable {//SOLID-Prinzip: Observer

    void addListener(ITruckListener listener);

    @SuppressWarnings("unused")
    void removeListener(ITruckListener listener);

    void notifyListeners(int id);
}
