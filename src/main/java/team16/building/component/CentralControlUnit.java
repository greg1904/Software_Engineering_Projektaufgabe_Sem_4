package team16.building.component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import team16.building.component.sortingSystem.PackageTrack;
import team16.command.ICommand;
import team16.command.InitCommand;
import team16.command.LockCommand;
import team16.command.NextCommand;
import team16.command.ShowStatisticsCommand;
import team16.command.ShutdownCommand;
import team16.command.UnlockCommand;
import team16.configuration.Configuration;
import team16.event.ControlCarEvent;
import team16.event.IEvent;
import team16.event.PackageTrackFullEvent;
import team16.event.StartRobotEvent;
import team16.event.StartSortingEvent;
import team16.event.TruckUnloadedEvent;

import java.util.HashSet;
import java.util.Set;

public class CentralControlUnit {
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
        if (filledPackageTracks.size() == Configuration.instance.packageTrackNum) {
            filledPackageTracks.clear();
            postEvent(new StartSortingEvent());
        }
    }

    public void truckArrived(int id) {
        System.out.println("Truck has arrived!");
        postEvent(new ControlCarEvent(id));
    }

}
