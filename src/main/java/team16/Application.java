package team16;

import team16.base.Configuration;
import team16.communication.commands.InitCommand;
import team16.communication.commands.NextCommand;
import team16.communication.commands.ShowStatisticsCommand;
import team16.data.datainstances.box.Box;
import team16.data.datainstances.packages.Package;
import team16.data.datainstances.packages.PackageType;
import team16.data.datainstances.pallet.Pallet;
import team16.data.transport.Truck;
import team16.dataio.CSVParser;
import team16.location.PackageSortingCenter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

public class Application {


    public static void main(String[] args) {
        if(!CSVParser.dataExists())
            CSVParser.createData();

        executeMainLoop();
    }

    private static void executeMainLoop() {
        PackageSortingCenter center = new PackageSortingCenter();
        center.pushCommand(new InitCommand(center.getParkingZone()));
        for(int i = 0; center.getTrucksDone() < Configuration.instance.truckCount; i++){
            center.pushCommand(new NextCommand(center.getParkingZone(), center.getUnloadZones()[i]));
        }
        center.pushCommand(new ShowStatisticsCommand(center.getTrucksDone(), center.getPackagesCount(), center.getForbiddenPackages()));
    }


}
