package team16.command;

import team16.building.component.Report;
import team16.configuration.Configuration;
import team16.storage.packet.PackageType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ShowStatisticsCommand implements ICommand {//SOLID-Prinzip: Command

    private final int truckCount;
    private final Map<PackageType, Integer> packagesCount;
    private final int explosiveCount;

    public ShowStatisticsCommand(int truckCount, Map<PackageType, Integer> packagesCount, int explosiveCount) {
        this.truckCount = truckCount;
        this.packagesCount = packagesCount;
        this.explosiveCount = explosiveCount;
    }

    @Override
    public void execute() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Configuration.INSTANCE.reportOutput, true))) {
            bw.write(new Report.Builder()
                    .addTimestamp()
                    .addExplosiveCount(explosiveCount)
                    .addPackageCount(packagesCount)
                    .addTruckCount(truckCount)
                    .build()
                    .toString()
            );
            System.out.println("Report printed!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
