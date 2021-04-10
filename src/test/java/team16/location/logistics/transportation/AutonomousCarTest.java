package team16.location.logistics.transportation;

import org.junit.jupiter.api.Test;
import team16.data.datainstances.box.Box;
import team16.data.datainstances.packages.Package;
import team16.data.datainstances.pallet.Pallet;
import team16.data.transport.Truck;
import team16.location.CentralControlUnit;
import team16.location.PackageSortingCenter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AutonomousCarTest {

    @Test
    void unloadZone() {
        Map<String, Package> packages = new HashMap<>(4800);

        while (packages.size() < 4800) {
            Package pack = new Package();
            packages.put(pack.getId(), pack);
        }

        Iterator<Package> packageIterator = packages.values().iterator();
        Map<String, Box> boxes = new HashMap<>(120);
        while (boxes.size() < 120) {
            Box box = new Box();
            while (box.hasRoom()) {
                box.addPackage(packageIterator.next());
            }
            boxes.put(box.getId(), box);
        }

        Iterator<Box> boxIterator = boxes.values().iterator();
        Map<Integer, Pallet> pallets = new HashMap<>(10);
        while (pallets.size() < 10) {
            Pallet pallet = new Pallet();

            while (pallet.hasRoom()) {
                pallet.addBox(boxIterator.next());
            }

            pallets.put(pallet.getId(), pallet);
        }

        Iterator<Pallet> palletIterator = pallets.values().iterator();
        Truck truck = new Truck("böx1");
        while (truck.hasRoom()) {
            truck.addPallet(palletIterator.next());
        }

        PackageSortingCenter sortingCenter = new PackageSortingCenter();
        try {                                   //Stopping the automatic scan start after the truck has been unloaded
            Method unregisterMethod = CentralControlUnit.class.getDeclaredMethod("unregister", Object.class);
            unregisterMethod.setAccessible(true);
            unregisterMethod.invoke(sortingCenter.getCentralControlUnit(), sortingCenter.getCentralControlUnit());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        sortingCenter.getUnloadZones()[0].addTruck(truck);      //starting the unloading process

        assertTrue(sortingCenter.getTemporaryPalletStorage().hasPallets());
        assertFalse(truck.hasLoad());

        Map<Integer, Pallet> palletMap = new HashMap<>();
        while (sortingCenter.getTemporaryPalletStorage().hasPallets()) {
            Pallet pallet = sortingCenter.getTemporaryPalletStorage().removePallet();
            palletMap.put(pallet.getId(), pallet);
        }

        for (Pallet pallet : palletMap.values()) {
            assertTrue(pallets.containsKey(pallet.getId()));

            while (pallet.hasLoad()) {
                Box box = pallet.getNextBox();
                assertTrue(boxes.containsKey(box.getId()));

                while (!box.isEmpty()) {
                    Package pack = box.removeNextPackage();
                    assertTrue(packages.containsKey(pack.getId()));
                    packages.remove(pack);
                }
                boxes.remove(box.getId());
            }

            pallets.remove(pallet.getId());
        }

    }
}