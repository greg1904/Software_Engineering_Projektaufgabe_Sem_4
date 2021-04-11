package team16.location;

import team16.communication.commands.ICommand;
import team16.communication.events.IEvent;
import team16.data.datainstances.packages.Package;
import team16.data.datainstances.packages.PackageType;
import team16.location.access.hardware.IDCardReader;
import team16.location.access.hardware.Terminal;
import team16.location.access.software.ProxyControlUnit;
import team16.location.logistics.temporarystorage.TemporaryPalletStorage;
import team16.location.logistics.zones.ParkingZone;
import team16.location.logistics.zones.UnloadZone;
import team16.location.sorting.SortingSystem;

import java.util.*;


public class PackageSortingCenter {
    private final CentralControlUnit centralControlUnit = new CentralControlUnit();
    private final UnloadZone[] unloadZones = new UnloadZone[7];
    private final ParkingZone parkingZone = new ParkingZone(5, this);
    private final SortingSystem sortingSystem = new SortingSystem(this);

    private final Terminal terminal = new Terminal(new IDCardReader(), new ProxyControlUnit(centralControlUnit));
    private final TemporaryPalletStorage temporaryPalletStorage = new TemporaryPalletStorage();
    private final Map<PackageType, Integer> packagesCount = new HashMap<>();
    private final List<Package> forbiddenPackages = new ArrayList<>();
    private int trucksDone;

    public PackageSortingCenter() {
        centralControlUnit.register(parkingZone);

        for (int i = 0; i < unloadZones.length; i++) {
            unloadZones[i] = new UnloadZone(centralControlUnit);
            register(unloadZones[i]);
        }
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
        centralControlUnit.postEvent(event);
    }

    public void pushCommand(ICommand command) {
        centralControlUnit.sendCommand(command);
    }

    public void register(Object obj) {
        centralControlUnit.register(obj);
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

    public CentralControlUnit getCentralControlUnit() {
        return centralControlUnit;
    }

    public SortingSystem getSortingSystem() {
        return sortingSystem;
    }
}
