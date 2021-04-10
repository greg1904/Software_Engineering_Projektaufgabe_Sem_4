package team16.location.logistics.sensors;

public interface IListenable {//SOLID-Prinzip: Observer

    void addListener(ITruckListener listener);

    @SuppressWarnings("unused")
    void removeListener(ITruckListener listener);

    void notifyListeners(int id);
}
