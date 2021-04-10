package team16.communication.commands;

import team16.data.transport.Truck;
import team16.dataio.CSVParser;
import team16.location.logistics.zones.ParkingZone;

import java.util.Iterator;

public class InitCommand implements ICommand { //SOLID-Prinzip: Command
    private final ParkingZone zone;

    public InitCommand(ParkingZone zone) {
        this.zone = zone;
    }

    @Override
    public void execute() {
        if (!zone.isFull()) {
            System.out.println("Parsing CSV Data.");

            int count = 0;

            Iterator<Truck> truckIterator = CSVParser.loadTrucks().iterator();
            while (truckIterator.hasNext()) {
                zone.addTruck(truckIterator.next());
                count++;
            }

            System.out.println(count + " Trucks have been added to the ParkingZone.");
        } else {
            System.out.println("ParkingZone is already full!");
        }
    }
}
