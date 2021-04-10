package team16;

import team16.base.Configuration;
import team16.communication.commands.InitCommand;
import team16.communication.commands.NextCommand;
import team16.communication.commands.ShowStatisticsCommand;
import team16.dataio.CSVParser;
import team16.employees.roles.DataAnalyst;
import team16.employees.roles.Supervisor;
import team16.location.PackageSortingCenter;

public class Application {
    public static void main(String[] args) {
        if (!CSVParser.checkDataExists()) {
            System.out.println("Creating CSV Files.");
            CSVParser.createData();
        }

        executeMainLoop();
    }

    private static void executeMainLoop() {
        PackageSortingCenter center = new PackageSortingCenter();

        Supervisor supervisor = new Supervisor("Rick Astley", 42069, 2021, 777983, true);
        center.getTerminal().insertCard(supervisor.getCard());
        center.getTerminal().login(2021);

        center.getTerminal().startFunctionOnTouchPad(new InitCommand(center.getParkingZone()));

        for (int i = 0; center.getTrucksDone() < Configuration.instance.truckCount; i++) {
            center.getTerminal().startFunctionOnTouchPad(new NextCommand(center.getParkingZone(), center.getUnloadZones()[i]));
        }

        center.getTerminal().ejectCard();

        DataAnalyst analyst = new DataAnalyst("Zack Snyder", 12348, 1156, 5525655);
        center.getTerminal().insertCard(analyst.getCard());
        center.getTerminal().login(1156);
        center.getTerminal().startFunctionOnTouchPad(
                new ShowStatisticsCommand(
                        center.getTrucksDone(), center.getPackagesCount(), center.getForbiddenPackages()
                ));
        center.getTerminal().ejectCard();
    }


}
