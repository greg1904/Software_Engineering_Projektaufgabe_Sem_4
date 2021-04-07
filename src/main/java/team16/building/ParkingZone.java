package team16.building;

import com.google.common.eventbus.Subscribe;
import team16.configuration.Configuration;
import team16.event.ControlCarEvent;
import team16.event.TruckUnloadedEvent;
import team16.vehicle.AutonomousCar;
import team16.vehicle.Truck;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;

@SuppressWarnings({"UnstableApiUsage"})
public class ParkingZone implements IZone {

    private final Queue<Truck> truckList;
    private final int maxTrucks;
    private final AutonomousCar[] cars = new AutonomousCar[Configuration.INSTANCE.autonomousCarNum];
    private final PackageSortingCenter center;

    public ParkingZone(int room, PackageSortingCenter center) {
        this.truckList = new LinkedList<>();
        this.maxTrucks = room;
        Arrays.setAll(cars, i -> new AutonomousCar(center));
        this.center = center;
    }

    @Subscribe
    public void receive(ControlCarEvent event) {
        getRandomCar().unloadZone(center.getZone(event.getId()));
    }

    @Subscribe
    public void receive(TruckUnloadedEvent event) {
        System.out.println("Autonomous Car drives back!");
        returnCar(event.getCar());
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addTruck(Truck truck) {
        if (hasRoom()) {
            truckList.add(truck);
            return true;
        }
        return false;
    }

    private boolean hasRoom() {
        return truckList.size() < maxTrucks;
    }

    public boolean isFull() {
        return truckList.size() >= maxTrucks;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean returnCar(AutonomousCar car) {
        if (availableCars() == cars.length) {
            return false;
        }
        IntStream.range(0, cars.length).filter(i -> cars[i] == null).findFirst().ifPresent(i -> cars[i] = car);
        return true;
    }

    public int availableCars() {
        return (int) Arrays.stream(cars).filter(Objects::nonNull).count();
    }

    @SuppressWarnings("unused")
    public AutonomousCar getCar() {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                AutonomousCar car = cars[i];
                cars[i] = null;
                return car;
            }
        }
        return null;
    }

    public AutonomousCar getRandomCar() {
        if (hasCarsLeft()) {
            AutonomousCar car;
            do {
                int index = new Random().nextInt(cars.length);
                car = cars[index];
                cars[index] = null;
            } while (car == null);
            System.out.println("Getting random Car");
            return car;
        }
        System.out.println("No Car left!");
        return null;
    }

    private boolean hasCarsLeft() {
        return Arrays.stream(cars).anyMatch(Objects::nonNull);
    }

    public Truck getNextTruck() {
        return !truckList.isEmpty() ? truckList.poll() : null;
    }
}
