package team16.command;

import team16.building.component.Report;
import team16.configuration.Configuration;
import team16.storage.packet.Package;
import team16.storage.packet.PackageType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShowStatisticsCommand implements ICommand { //SOLID-Prinzip: Command
    private final int truckCount;
    private final Map<PackageType, Integer> packagesCount;
    private final List<Package> explosivePackages;

    public ShowStatisticsCommand(int truckCount, Map<PackageType, Integer> packagesCount, List<Package> explosivePackages) {
        this.truckCount = truckCount;
        this.packagesCount = packagesCount;
        this.explosivePackages = explosivePackages;
    }

    @Override
    public void execute() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Configuration.instance.reportOutput, true))) {
            bw.write(new Report.Builder()
                    .addTimestamp()
                    .addExplosivePackages(explosivePackages)
                    .addPackageCount(packagesCount)
                    .addTruckCount(truckCount)
                    .build()
                    .toString()
            );
            System.out.println("Report has been printed into the report.txt file!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
