package team16.location.logistics.sensors;

import javax.swing.event.EventListenerList;
import java.util.Arrays;

public class TruckArrivalSensor implements IListenable { //SOLID-Prinzip: Observer
    private final EventListenerList list = new EventListenerList();
    private boolean activated = true;

    @Override
    public void addListener(ITruckListener listener) {
        list.add(ITruckListener.class, listener);
    }

    public void activate() {
        System.out.println("Activated");
        activated = true;
    }

    public void deactivate() {
        System.out.println("Deactivated");
        activated = false;
    }

    @Override
    public void removeListener(ITruckListener listener) {
        list.remove(ITruckListener.class, listener);
    }

    @Override
    public void notifyListeners(int id) {
        if (activated) {
            System.out.println("Notifying TruckListeners");

            Arrays.stream(list.getListeners(ITruckListener.class))
                    .forEach(listener -> listener.truckArrived(id));
        }
    }
}
