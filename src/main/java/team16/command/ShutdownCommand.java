package team16.command;

import team16.building.PackageSortingCenter;
import team16.building.UnloadZone;
import team16.event.ProcessSortingTracksEvent;

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
