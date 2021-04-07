package team16.building.component;

public interface IListenable {//SOLID-Prinzip: Observer

    void addListener(ITruckListener listener);

    @SuppressWarnings("unused")
    void removeListener(ITruckListener listener);

    void notifyListeners(int id);
}
