package team16.building;

import team16.building.component.ControlUnit;
import team16.building.component.Terminal;
import team16.building.component.sortingSystem.SortingSystem;
import team16.command.ICommand;
import team16.configuration.Configuration;
import team16.event.IEvent;
import team16.security.ProxyControlUnit;
import team16.security.authorization.CardReader;
import team16.storage.packet.PackageType;
import team16.storage.room.interim.InterimPalletStorage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PackageSortingCenter {

    private final ControlUnit unit = new ControlUnit();
    private final UnloadZone[] unloadZones = new UnloadZone[Configuration.INSTANCE.unloadZoneNum];
    private final ParkingZone parkingZone = new ParkingZone(Configuration.INSTANCE.parkingZoneTruckCount, this);
    @SuppressWarnings("unused")
    private final SortingSystem sortingSystem = new SortingSystem(this);
    private final Terminal terminal = new Terminal(new CardReader(), new ProxyControlUnit(unit));
    private final InterimPalletStorage interimPalletStorage = new InterimPalletStorage();
    private final Map<PackageType, Integer> packagesCount = new HashMap<>();
    private int trucksDone;
    private int forbiddenPackages;

    public PackageSortingCenter() {
        unit.register(parkingZone);
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

    public InterimPalletStorage getInterimPalletStorage() {
        return interimPalletStorage;
    }

    public void pushEvent(IEvent event) {
        unit.pushEvent(event);
    }

    @SuppressWarnings("unused")
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

    public void incrementForbiddenPackages() {
        forbiddenPackages++;
    }

    public int getTrucksDone() {
        return trucksDone;
    }

    public int getForbiddenPackages() {
        return forbiddenPackages;
    }

    public Map<PackageType, Integer> getPackagesCount() {
        return packagesCount;
    }
}
