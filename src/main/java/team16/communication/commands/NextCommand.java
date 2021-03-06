package team16.communication.commands;

import team16.location.logistics.zones.ParkingZone;
import team16.location.logistics.zones.UnloadZone;

public class NextCommand implements ICommand { //SOLID-Prinzip: Command
    private final ParkingZone parkingZone;
    private final UnloadZone unloadZone;

    public NextCommand(ParkingZone parkingZone, UnloadZone unloadZone) {
        this.parkingZone = parkingZone;
        this.unloadZone = unloadZone;
    }

    @Override
    public void execute() {
        if (unloadZone == null) {
            System.out.println("All UnloadZones are in use!");
        } else {
            unloadZone.addTruck(parkingZone.getNextTruck());
        }
    }
}
