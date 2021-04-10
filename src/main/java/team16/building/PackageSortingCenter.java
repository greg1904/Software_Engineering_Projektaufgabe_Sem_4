package team16.building;

import team16.building.component.CentralControlUnit;
import team16.building.component.Terminal;
import team16.building.component.sortingSystem.SortingSystem;
import team16.command.ICommand;
import team16.configuration.Configuration;
import team16.event.IEvent;
import team16.security.ProxyControlUnit;
import team16.security.authorization.IDCardReader;
import team16.storage.packet.Package;
import team16.storage.packet.PackageType;
import team16.storage.room.interim.TemporaryPalletStorage;

import java.util.*;


public class PackageSortingCenter {
    private final CentralControlUnit unit = new CentralControlUnit();
    private final UnloadZone[] unloadZones = new UnloadZone[Configuration.instance.unloadZoneNum];
    private final ParkingZone parkingZone = new ParkingZone(Configuration.instance.parkingZoneAutoCarCount, this);
    private final SortingSystem sortingSystem = new SortingSystem(this);

    private final Terminal terminal = new Terminal(new IDCardReader(), new ProxyControlUnit(unit));
    private final TemporaryPalletStorage temporaryPalletStorage = new TemporaryPalletStorage();
    private final Map<PackageType, Integer> packagesCount = new HashMap<>();

    private int trucksDone;
    private final List<Package> forbiddenPackages = new ArrayList<>();

    public PackageSortingCenter() {
        unit.register(parkingZone);

//        for(int i=0; i< unloadZones.length; i++){ //TODO WHAT IS THIS
//            unloadZones[i] = new UnloadZone();
//            register(unloadZones[i]);
//        }

        Arrays.setAll(unloadZones, i -> new UnloadZone(unit::truckArrived));
        Arrays.stream(unloadZones).forEach(this::register);
    }

    public UnloadZone getZone(int id) {
        return Arrays.stream(unloadZones)
                .filter(zone -> zone.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public UnloadZone[] getUnloadZones() {
        return unloadZones;
    }

    public UnloadZone getNextFreeZone() {
        return Arrays.stream(unloadZones)
                .filter(zone -> zone.getTruck() == null)
                .findAny()
                .orElse(null);
    }

    public TemporaryPalletStorage getTemporaryPalletStorage() {
        return temporaryPalletStorage;
    }

    public void postEvent(IEvent event) {
        unit.postEvent(event);
    }

    public void pushCommand(ICommand command) {
        unit.sendCommand(command);
    }

    public void register(Object obj) {
        unit.register(obj);
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public ParkingZone getParkingZone() {
        return parkingZone;
    }

    public void incrementTrucksDone() {
        trucksDone++;
    }

    public void incrementPackages(PackageType type) {
        if (packagesCount.containsKey(type)) {
            packagesCount.put(type, packagesCount.get(type) + 1);
        } else {
            packagesCount.put(type, 1);
        }
    }

    public void addForbiddenPackage(Package forbiddenPackage) {
        forbiddenPackages.add(forbiddenPackage);
    }

    public int getTrucksDone() {
        return trucksDone;
    }

    public List<Package> getForbiddenPackages() {
        return forbiddenPackages;
    }

    public Map<PackageType, Integer> getPackagesCount() {
        return packagesCount;
    }
}
