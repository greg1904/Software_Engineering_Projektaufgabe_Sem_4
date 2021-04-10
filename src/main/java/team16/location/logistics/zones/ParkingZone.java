package team16.location.logistics.zones;

import com.google.common.eventbus.Subscribe;
import team16.base.Configuration;
import team16.communication.events.ControlCarEvent;
import team16.communication.events.TruckUnloadedEvent;
import team16.data.transport.Truck;
import team16.location.PackageSortingCenter;
import team16.location.logistics.transportation.AutonomousCar;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;

public class ParkingZone {
    private final AutonomousCar[] cars = new AutonomousCar[5];
    private final Queue<Truck> truckList;
    private final int maxTrucks;
    private final PackageSortingCenter center;

    public ParkingZone(int room, PackageSortingCenter center) {
        this.truckList = new LinkedList<>();
        this.maxTrucks = room;

        for (int i = 0; i < cars.length; i++)
            cars[i] = new AutonomousCar(center);

        this.center = center;
    }

    @Subscribe
    public void receive(ControlCarEvent event) {
        getRandomCar().unloadZone(center.getZone(event.getId()));
    }

    @Subscribe
    public void receive(TruckUnloadedEvent event) {
        System.out.println("Autonomous Car is returning!");
        returnCar(event.getCar());
    }

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

    public boolean returnCar(AutonomousCar car) {
        if (availableCars() == cars.length) {
            return false;
        }
        IntStream.range(0, cars.length).filter(i -> cars[i] == null).findFirst().ifPresent(i -> cars[i] = car);
        return true;
    }

    public int availableCars() {
        int count = 0;

        for (AutonomousCar car : cars)
            if (car != null)
                count++;

        return count;
    }

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
                car = cars[new Random().nextInt(cars.length)];
                cars[index] = null;
            } while (car == null);
            System.out.println("Selected random Car");
            return car;
        }
        System.out.println("No Cars available!");
        return null;
    }

    private boolean hasCarsLeft() {
        for (AutonomousCar car : cars)
            if (car != null)
                return true;

        return false;
    }

    public Truck getNextTruck() {
        return !truckList.isEmpty() ? truckList.poll() : null;
    }
}
