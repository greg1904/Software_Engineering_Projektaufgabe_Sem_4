package team16.building.component;

import team16.storage.packet.Package;
import team16.storage.packet.PackageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Report {
    private final boolean addTimestamp;
    private final int truckCount;
    private final Map<PackageType, Integer> packagesCount;
    private final List<Package> explosivePackages;

    private Report(Builder builder) {
        addTimestamp = builder.addTimestamp;
        truckCount = builder.truckCount;
        packagesCount = builder.packagesCount;
        explosivePackages = builder.explosivePackages;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (addTimestamp) {
            builder.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")))
                    .append(": ");
        }
        builder.append("Processed Trucks: ")
                .append(truckCount)
                .append(System.lineSeparator())
                .append("\t\t\t ").append("##Packages: ");

        Iterator<Map.Entry<PackageType, Integer>> iterator = packagesCount.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry<PackageType, Integer> data = iterator.next();

            builder.append(data.getKey()).append(" - ").append(data.getValue()).append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append(System.lineSeparator());

        builder.append("\t\t\t ").append("##Explosive Packages: ").append(System.lineSeparator());

        if(explosivePackages.size() != 0){
            for(Package p:explosivePackages){
                builder.append("\t\t\t\t").append(p.getId()).append(": ").append(p.getType()).append(System.lineSeparator());
            }
        }else{
            builder.append("\t\t\t\t\t No explosives have been found yet.").append(System.lineSeparator());
        }
//
//        packagesCount.keySet().forEach(type ->
//                builder.append(type)
//                        .append(": ")
//                        .append(packagesCount.get(type))
//                        .append(", "));
//        builder.delete(builder.length() - 2, builder.length());
//        builder.append(System.lineSeparator())
//                .append("\t\t\t ").append("Packages with Explosives: ")
//                .append(explosiveCount)
//                .append(System.lineSeparator());

        return builder.toString();
    }

    public static class Builder { //SOLID-Prinzip: Builder
        private boolean addTimestamp;
        private int truckCount;
        private final Map<PackageType, Integer> packagesCount = new HashMap<>();
        private List<Package> explosivePackages = new ArrayList<>();

        public Builder addTimestamp() {
            addTimestamp = true;
            return this;
        }

        public Builder addTruckCount(int count) {
            truckCount += count;
            return this;
        }

        public Builder addPackageCount(PackageType type, int count) {
            if (packagesCount.containsKey(type)) {
                packagesCount.put(type, packagesCount.get(type) + count);
            } else {
                packagesCount.put(type, count);
            }
            return this;
        }

        public Builder addPackageCount(Map<PackageType, Integer> packagesCount) {
            Iterator<Map.Entry<PackageType, Integer>> iterator = packagesCount.entrySet().iterator();

            while(iterator.hasNext()){
                Map.Entry<PackageType, Integer> data = iterator.next();
                addPackageCount(data.getKey(), data.getValue());
            }

            return this;
        }

        public Builder addExplosivePackage(Package explosivePackage){
            explosivePackages.add(explosivePackage);
            return this;
        }

        public Builder addExplosivePackages(List<Package> explosivePackages) {
            for(Package p: explosivePackages){
                addExplosivePackage(p);
            }

            return this;
        }
//        public Builder addExplosiveCount(int count) {
//            explosiveCount += count;
//            return this;
//        }

        public Report build() {
            return new Report(this);
        }

    }
}
