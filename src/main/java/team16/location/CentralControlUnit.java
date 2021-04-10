package team16.location;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import team16.base.Configuration;
import team16.communication.commands.*;
import team16.communication.events.*;
import team16.location.logistics.sensors.ITruckListener;
import team16.location.sorting.structure.PackageTrack;

import java.util.HashSet;
import java.util.Set;

public class CentralControlUnit implements ITruckListener {
    private final EventBus bus = new EventBus("PackageSortingCenter");
    private final Set<PackageTrack> filledPackageTracks = new HashSet<>();

    public CentralControlUnit() {
        bus.register(this);
    }

    public void sendCommand(ICommand command) {
        bus.post(command);
    }

    public void postEvent(IEvent event) {
        bus.post(event);
    }

    public void register(Object obj) {
        bus.register(obj);
    }

    private void unregister(Object obj) {
        bus.unregister(obj);
    }

    @Subscribe
    public void receive(InitCommand command) {
        command.execute();
    }

    @Subscribe
    public void receive(NextCommand command) {
        command.execute();
    }

    @Subscribe
    public void receive(ShutdownCommand command) {
        command.execute();
    }

    @Subscribe
    public void receive(ShowStatisticsCommand command) {
        command.execute();
    }

    @Subscribe
    public void receive(LockCommand command) {
        command.execute();
    }

    @Subscribe
    public void receive(UnlockCommand command) {
        command.execute();
    }

    @Subscribe
    public void receive(TruckUnloadedEvent event) {
        postEvent(new StartRobotEvent());
    }

    @Subscribe
    public void receive(PackageTrackFullEvent event) {
        filledPackageTracks.add(event.getTrack());
        if (filledPackageTracks.size() == 8) {
            filledPackageTracks.clear();
            postEvent(new StartSortingEvent());
        }
    }

    @Override
    public void truckArrived(int id) {
        System.out.println("Truck has arrived!");
        postEvent(new ControlCarEvent(id));
    }

}
