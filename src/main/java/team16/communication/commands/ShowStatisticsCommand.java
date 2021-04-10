package team16.communication.commands;

import team16.data.datainstances.packages.Package;
import team16.data.datainstances.packages.PackageType;
import team16.dataio.Report;
import team16.base.Configuration;

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
