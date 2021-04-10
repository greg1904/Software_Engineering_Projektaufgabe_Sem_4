package team16.communication.commands;

import team16.location.PackageSortingCenter;
import team16.communication.events.ProcessSortingTracksEvent;
import team16.location.logistics.zones.UnloadZone;

public class ShutdownCommand implements ICommand { //SOLID-Prinzip: Command
    private final UnloadZone unloadZone;
    private final PackageSortingCenter sortingCenter;

    public ShutdownCommand(UnloadZone unloadZone, PackageSortingCenter sortingCenter) {
        this.unloadZone = unloadZone;
        this.sortingCenter = sortingCenter;
    }

    @Override
    public void execute() {
        System.out.println("Shutting down.");
        unloadZone.getSensor().deactivate();
        sortingCenter.postEvent(new ProcessSortingTracksEvent());
    }
}
