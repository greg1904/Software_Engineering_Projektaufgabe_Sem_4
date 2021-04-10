package team16;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import team16.building.PackageSortingCenter;
import team16.building.UnloadZone;
import team16.building.component.Terminal;
import team16.command.ChangeSearchAlgorithmCommand;
import team16.command.InitCommand;
import team16.command.NextCommand;
import team16.command.ShowStatisticsCommand;
import team16.command.ShutdownCommand;
import team16.employee.Supervisor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SetupTest {

    private PackageSortingCenter center;

    @BeforeEach
    public void init() {
        center = new PackageSortingCenter();
    }

    @Test
    @Order(1)
    public void unloading() {
        assertNotNull(center);//Test Nummer 1 -> Zentrum wird erstellt.
        Terminal terminal = center.getTerminal();
        assertNotNull(terminal);
        Supervisor supervisor = new Supervisor("Andreas Köhler", 8596, 1996, 851996, true);
        assertFalse(terminal.login(1996));
        assertTrue(terminal.insertCard(supervisor.getCard()));
        assertFalse(terminal.startFunctionOnTouchPad(new ChangeSearchAlgorithmCommand()));
        assertTrue(terminal.login(1996));
        assertTrue(terminal.startFunctionOnTouchPad(new InitCommand(center.getParkingZone())));
        for (int i = 0; i < 6; i++) {
            assertTrue(terminal.startFunctionOnTouchPad(
                    new NextCommand(center.getParkingZone(), center.getNextFreeZone())));
        }
        assertTrue(terminal.startFunctionOnTouchPad(
                new ShowStatisticsCommand(
                        center.getTrucksDone(),
                        center.getPackagesCount(),
                        center.getForbiddenPackages())));
        for (UnloadZone zone : center.getUnloadZones()) {
            assertTrue(terminal.startFunctionOnTouchPad(new ShutdownCommand(zone, center)));
        }
        assertNotNull(terminal.ejectCard());
    }

    @AfterEach
    public void close() {
        center = null;
    }
}
