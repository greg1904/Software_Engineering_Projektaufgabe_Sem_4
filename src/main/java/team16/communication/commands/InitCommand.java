package team16.communication.commands;

import team16.data.datainstances.packages.Package;
import team16.base.Configuration;
import team16.data.datainstances.box.Box;
import team16.data.datainstances.packages.PackageType;
import team16.data.datainstances.pallet.Pallet;
import team16.data.transport.Truck;
import team16.dataio.CSVParser;
import team16.location.logistics.zones.ParkingZone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InitCommand implements ICommand { //SOLID-Prinzip: Command
    private final ParkingZone zone;

    public InitCommand(ParkingZone zone) {
        this.zone = zone;
    }

    @Override
    public void execute() {
        if (!zone.isFull()) {
            System.out.println("Parsing CSV Data.");

            int count = 0;

            Iterator<Truck> truckIterator = CSVParser.loadTrucks().iterator();
            while(truckIterator.hasNext()){
                zone.addTruck(truckIterator.next());
                count++;
            }
//            getTrucks().forEach(zone::addTruck);
            System.out.println(count + " Trucks have been added to the ParkingZone.");
        } else {
            System.out.println("ParkingZone is already full!");
        }
    }

//    private List<Package> getPackages() {
//        List<Package> packages = new ArrayList<>(1);
//        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.instance.packageOutput))) {
//            packages = br.lines()
//                    .map(line -> Arrays.stream(line.split(","))
//                            .map(c -> c.substring(1, c.length() - 1))
//                            .toArray(String[]::new))
//                    .map(pck -> new Package(pck[0], pck[1], Integer.parseInt(pck[2]), PackageType.valueOf(pck[3]), Double.parseDouble(pck[4])))
//                    .collect(Collectors.toCollection(() -> new ArrayList<>(Configuration.instance.packageCount)));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        System.out.println("Read " + packages.size() + " Packages");
//        return packages;
//    }
//
//    private List<Box> getBoxes() {
//        List<Package> packages = getPackages();
//        List<Box> boxes = new ArrayList<>(1);
//        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.instance.boxOutput))) {
//            boxes = br.lines()
//                    .map(line -> Arrays.stream(line.split(","))
//                            .map(c -> c.substring(1, c.length() - 1))
//                            .toArray(String[]::new))
//                    .map(pck -> new Box(pck[0]))
//                    .collect(Collectors.toCollection(() -> new ArrayList<>(Configuration.instance.boxesCount)));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        for (int i = 0, count = 0; i < boxes.size(); i++) {
//            while (boxes.get(i).hasRoom()) {
//                boxes.get(i).addPackage(packages.get(count++));
//            }
//        }
//        System.out.println("Read " + boxes.size() + " Boxes");
//        return boxes;
//    }
//
//    private List<Pallet> getPallets() {
//        List<Box> boxes = getBoxes();
//        List<Pallet> pallets = new ArrayList<>(Configuration.instance.palletCount);
//        Map<String, List<String>> palletMap = new HashMap<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.instance.palletOutput))) {
//            palletMap = br.lines()
//                    .filter(line -> !line.trim().isEmpty())
//                    .collect(Collectors.groupingBy(line -> line.substring(0, line.indexOf(","))));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        String lastId = "";
//        Pallet pall = null;
//        for (int i = 0; i < palletMap.size(); i++) {
//            List<String> keys = new ArrayList<>(palletMap.keySet());
//            String id = palletMap.get(keys.get(i)).get(0).split(",")[0].substring(1);
//            if (!lastId.equals(id)) {
//                if (pall != null) {
//                    pallets.add(pall);
//                }
//                pall = new Pallet(Integer.parseInt(id.substring(0, id.length() - 1)));
//                lastId = id;
//            }
//            Pallet finalPall = pall;
//            palletMap.get(keys.get(i)).forEach(palletString -> {
//                String[] pieces = palletString.split(",");
//                Arrays.setAll(pieces, j -> pieces[j].substring(1));
//                finalPall.addBox(boxes.stream()
//                                .filter(box -> box.getId().equals(pieces[3].substring(0, pieces[3].length() - 1)))
//                                .findFirst()
//                                .orElseThrow(),
//                        Integer.parseInt(pieces[1].substring(0, pieces[1].length() - 1)),
//                        Integer.parseInt(pieces[2].substring(0, pieces[2].length() - 1))
//                );
//            });
//        }
//        if (pall != null) {
//            pallets.add(pall);
//        }
//        System.out.println("Read " + pallets.size() + " Pallets");
//        return pallets;
//    }
//
//    private List<Truck> getTrucks() {
//        List<Pallet> pallets = getPallets();
//        List<Truck> trucks = new ArrayList<>(Configuration.instance.truckCount);
//        Map<String, List<String>> truckMap = new HashMap<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.instance.truckOutput))) {
//            truckMap = br.lines()
//                    .filter(line -> !line.trim().isEmpty())
//                    .collect(Collectors.groupingBy(line -> line.substring(0, line.indexOf(","))));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        String lastId = "";
//        Truck truck = null;
//        for (int i = 0; i < truckMap.size(); i++) {
//            List<String> keys = new ArrayList<>(truckMap.keySet());
//            String id = truckMap.get(keys.get(i)).get(0).split(",")[0].substring(1);
//            if (!lastId.equals(id)) {
//                if (truck != null) {
//                    trucks.add(truck);
//                }
//                truck = new Truck(id.substring(0, id.length() - 1));
//                lastId = id;
//            }
//            Truck finalTruck = truck;
//            truckMap.get(keys.get(i)).stream().map(palletString -> palletString.split(",")).forEach(pieces -> {
//                Arrays.setAll(pieces, j -> pieces[j].substring(1, pieces[j].length() - 1));
//                switch (pieces[1]) {
//                    case "left" -> finalTruck.addPalletLeft(pallets.stream()
//                                    .filter(pallet -> pallet.getId() == Integer.parseInt(pieces[3]))
//                                    .findFirst()
//                                    .orElseThrow(),
//                            Integer.parseInt(pieces[2]));
//                    case "right" -> finalTruck.addPalletRight(pallets.stream()
//                                    .filter(pallet -> pallet.getId() == Integer.parseInt(pieces[3]))
//                                    .findFirst()
//                                    .orElseThrow(),
//                            Integer.parseInt(pieces[2]));
//                }
//            });
//        }
//        if (truck != null) {
//            trucks.add(truck);
//        }
//        System.out.println("Read " + trucks.size() + " trucks");
//        return trucks;
//    }
}
