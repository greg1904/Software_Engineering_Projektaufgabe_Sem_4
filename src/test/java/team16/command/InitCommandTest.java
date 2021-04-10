package team16.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import team16.building.PackageSortingCenter;
import team16.building.ParkingZone;
import team16.configuration.Configuration;
import team16.storage.box.Box;
import team16.storage.box.BoxLayer;
import team16.storage.packet.Package;
import team16.storage.pallet.Pallet;
import team16.vehicle.Truck;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InitCommandTest {

    private InitCommand command;
    private ParkingZone zone;

    @BeforeEach
    void setUp() {
        zone = new PackageSortingCenter().getParkingZone();
        command = new InitCommand(zone);
    }

    @Test
    @Order(1)
    public void execute() {
        long start = System.nanoTime();

        command.execute();

        long now = System.nanoTime();
        printTime("Reading", Duration.ofNanos(now - start));

        start = System.nanoTime();
        int truckCount = 0;
        int palletCount = 0;
        int boxCount = 0;
        int packageCount = 0;
        for (int i = 0; i < Configuration.instance.parkingZoneTruckCount; i++) {
            Truck truck = zone.getNextTruck();
            assertNotNull(truck);
            truckCount++;
            while (truck.hasLoad()) {
                Pallet pallet = truck.getNextPallet();
                assertNotNull(pallet);
                palletCount++;
                while (pallet.hasLoad()) {
                    Box box = pallet.getNextBox();
                    assertNotNull(box);
                    boxCount++;
                    for (BoxLayer layer : box.getBoxLayers()) {
                        while (layer.hasLoad()) {
                            Package packet = layer.getNextPackage();
                            assertNotNull(packet);
                            packageCount++;
                        }
                    }
                }
            }
        }

        now = System.nanoTime();
        printTime("Counting", Duration.ofNanos(now - start));
        assertEquals(5, truckCount);
        assertEquals(50, palletCount);
        assertEquals(600, boxCount);
        assertEquals(24_000, packageCount);
    }

    private void printTime(String type, Duration d) {
        System.out.printf("%s finished: %02d:%02d.%03d" + System.lineSeparator(), type, d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart());
    }
}