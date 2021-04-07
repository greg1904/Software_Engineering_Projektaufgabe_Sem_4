package team16.command;

import team16.building.PackageSortingCenter;
import team16.building.UnloadZone;
import team16.event.ProcessSortingTracksEvent;

public class ShutdownCommand implements ICommand {//SOLID-Prinzip: Command

    private final UnloadZone zone;
    private final PackageSortingCenter center;

    public ShutdownCommand(UnloadZone zone, PackageSortingCenter center) {
        this.zone = zone;
        this.center = center;
    }

    @Override
    public void execute() {
        zone.getSensor().deactivate();
        System.out.println("Shutdown");
        center.pushEvent(new ProcessSortingTracksEvent());
    }
}
