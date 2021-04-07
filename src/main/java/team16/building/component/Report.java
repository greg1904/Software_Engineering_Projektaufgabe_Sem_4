package team16.building.component;

import team16.storage.packet.PackageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Report {

    private final boolean addTimestamp;
    private final int truckCount;
    private final Map<PackageType, Integer> packagesCount;
    private final int explosiveCount;

    private Report(Builder builder) {
        addTimestamp = builder.addTimestamp;
        truckCount = builder.truckCount;
        packagesCount = builder.packagesCount;
        explosiveCount = builder.explosiveCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (addTimestamp) {
            builder.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")))
                    .append(": ");
        }
        builder.append("Dispatched Trucks: ")
                .append(truckCount)
                .append(System.lineSeparator())
                .append("\t\t\t\t\t\t ")
                .append("Package's: ");

        packagesCount.keySet().forEach(type ->
                builder.append(type)
                        .append(": ")
                        .append(packagesCount.get(type))
                        .append(", "));
        builder.delete(builder.length() - 2, builder.length());
        return builder.append(System.lineSeparator())
                .append("\t\t\t\t\t\t ")
                .append("Packages with Explosives: ")
                .append(explosiveCount)
                .append(System.lineSeparator())
                .toString();
    }

    public static class Builder {//SOLID-Prinzip: Builder

        private final Map<PackageType, Integer> packagesCount = new HashMap<>();
        private boolean addTimestamp;
        private int truckCount;
        private int explosiveCount;

        public Builder addTimestamp() {
            addTimestamp = true;
            return this;
        }

        public Builder addTruckCount(int count) {
            truckCount += count;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Builder addPackageCount(PackageType type, int count) {
            if (packagesCount.containsKey(type)) {
                packagesCount.put(type, packagesCount.get(type) + count);
            } else {
                packagesCount.put(type, count);
            }
            return this;
        }

        public Builder addPackageCount(Map<PackageType, Integer> packagesCount) {
            packagesCount.keySet().forEach(type ->
                    addPackageCount(type, packagesCount.get(type))
            );
            return this;
        }

        public Builder addExplosiveCount(int count) {
            explosiveCount += count;
            return this;
        }

        public Report build() {
            return new Report(this);
        }

    }
}
