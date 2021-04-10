package team16.location;

import org.junit.jupiter.api.Test;
import team16.location.logistics.zones.UnloadZone;

import static org.junit.jupiter.api.Assertions.*;

class PackageSortingCenterTest {
    @Test
    void setupTest() {
        PackageSortingCenter sortingCenter = new PackageSortingCenter();
        assertNotNull(sortingCenter);
        assertNotNull(sortingCenter.getCentralControlUnit());
        assertEquals(7, sortingCenter.getUnloadZones().length);

        UnloadZone[] zonesCopy = sortingCenter.getUnloadZones();

        for (int i = 0; i < sortingCenter.getUnloadZones().length; i++) {
            assertNotNull(sortingCenter.getUnloadZones()[i]);

            for (int j = 0; j < zonesCopy.length; j++) {
                if (j != i)
                    assertNotEquals(zonesCopy[j], sortingCenter.getUnloadZones()[i]);
            }
        }

        assertNotNull(sortingCenter.getParkingZone());
        assertEquals(5, sortingCenter.getParkingZone().availableCars());
        assertNotNull(sortingCenter.getSortingSystem());
    }
}