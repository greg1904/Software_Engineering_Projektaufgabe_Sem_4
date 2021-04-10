package team16.dataio;

import team16.base.Configuration;
import team16.data.datainstances.box.Box;
import team16.data.datainstances.packages.Package;
import team16.data.datainstances.packages.PackageType;
import team16.data.datainstances.pallet.Pallet;
import team16.data.transport.Truck;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CSVParser {
    private static HashMap<String, Package> packagesMap;
    private static HashMap<String, Box> boxesMap;
    private static HashMap<Integer, Pallet> palletsMap;
    private static HashMap<String, Truck> trucksMap;

    private static void createPackages() {
        StringBuilder packageBuilder = new StringBuilder(Configuration.instance.packageContentSize[0] *
                Configuration.instance.packageContentSize[1] *
                Configuration.instance.packageContentSize[2] *
                Configuration.instance.packageCount);

        List<Package> generatedPackages = new ArrayList<>(Configuration.instance.packageCount);
        packagesMap = new HashMap<>(Configuration.instance.packageCount);

        for(int i = 0; i< Configuration.instance.packageCount; i++){
            generatedPackages.add(new Package());
        }

        Collections.shuffle(generatedPackages);

        Random r = new Random();
        List<Integer> randomPositions = new ArrayList<>();
        for(int i = 0; i<4; i++){
            int rand = r.nextInt(generatedPackages.size());
            while (randomPositions.contains(rand)){
                rand = r.nextInt(generatedPackages.size());
            }
            randomPositions.add(rand);
        }

        for(Integer position : randomPositions){
            generatedPackages.get(position).addContent("exp!os:ve");
        }

////        packages = IntStream.range(0, Configuration.instance.packageCount)
////                .parallel().mapToObj(i -> new Package())
////                .collect(Collectors.toCollection(() -> new ArrayList<>(Configuration.instance.packageCount)));
////        Collections.shuffle(packages);
//        Random r = new Random();
//        IntStream.range(0, 4).forEach(i -> packages.get(r.nextInt(packages.size())).addContent("exp!os:ve"));


        for(Package p: generatedPackages){
            packagesMap.put(p.getId(), p);

            packageBuilder.append(String.format(Locale.US, "[%s],[%s],[%05d],[%s],[%.2f]",
                    p.getId(), p.getContentAsString(), p.getZipCode(), p.getType().name(), p.getWeight()));
            packageBuilder.append(System.lineSeparator());
        }

        writeToFile(new File(Configuration.instance.packageOutput), packageBuilder.toString());
    }

    private static void createBoxes() {
        StringBuilder boxBuilder = new StringBuilder();
        boxesMap = new HashMap<>(Configuration.instance.boxesCount);

        List<Box> boxes = new ArrayList<>(Configuration.instance.boxesCount);

        for(int i = 0; i< Configuration.instance.boxesCount; i++){
            boxes.add(new Box());
        }


        Iterator<Package> packageIterator = packagesMap.values().iterator();
        for(Box box: boxes){
            boxBuilder.append(String.format("[%s]", box.getId()));

            while(box.hasRoom() && packageIterator.hasNext()){
                Package pack = packageIterator.next();
                box.addPackage(pack);
                boxBuilder.append(String.format(",[%s]", pack.getId()));
            }
            boxBuilder.append(System.lineSeparator());
            boxesMap.put(box.getId(), box);
        }

        writeToFile(new File(Configuration.instance.boxOutput), boxBuilder.toString());
    }

    private static void createPallets() {
        StringBuilder palletBuilder = new StringBuilder();
        palletsMap = new HashMap<>(Configuration.instance.palletCount);
        List<Pallet> pallets = new ArrayList<>(Configuration.instance.palletCount);

        for(int i=0; i<Configuration.instance.palletCount; i++){
            pallets.add(new Pallet());
        }

        Iterator<Box> boxIterator = boxesMap.values().iterator();
        for(Pallet p : pallets){
            while (p.hasRoom() && boxIterator.hasNext()) {
                Box box = boxIterator.next();
                p.addBox(box);
                palletBuilder.append(String.format("[%d],[%d],[%d],[%s]", p.getId(), p.getPositionIndex(box), p.getLayerIndex(box), box.getId()));
                palletBuilder.append(System.lineSeparator());
            }

            palletsMap.put(p.getId(), p);
        }
//        pallets.forEach(pallet -> {
//            while (pallet.hasRoom() && iterator.hasNext()) {
//                Box box = iterator.next();
//                pallet.addBox(box);
//                palletBuilder.append(String.format("[%d],[%d],[%d],[%s]", pallet.getId(), pallet.getPositionIndex(box), pallet.getLayerIndex(box), box.getId()));
//                palletBuilder.append(System.lineSeparator());
//            }
//        });

        writeToFile(new File(Configuration.instance.palletOutput), palletBuilder.toString());
    }

    private static void createTrucks() {
        StringBuilder truckBuilder = new StringBuilder();
        trucksMap = new HashMap<>();
        List<Truck> trucks = new ArrayList<>();

        for(int i=0; i<Configuration.instance.truckCount; i++){
            trucks.add(new Truck());
        }


        Iterator<Pallet> palletIterator = palletsMap.values().iterator();

        for(Truck t:trucks){
            while (t.hasRoom() && palletIterator.hasNext()) {
                Pallet pallet = palletIterator.next();
                t.addPallet(pallet);
                int[] positions = t.getPosition(pallet);
                truckBuilder.append(String.format("[%s],[%s],[%d],[%d]", t.getId(), positions[0] == 0 ? "left" : "right", positions[1], pallet.getId()));
                truckBuilder.append(System.lineSeparator());
            }
            trucksMap.put(t.getId(), t);
        }

        writeToFile(new File(Configuration.instance.truckOutput), truckBuilder.toString());
    }

    private static void writeToFile(File f, String s) {
        f.delete();

        System.out.println("Writing data in File: " + f.getAbsolutePath());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f), s.length())) {
            bw.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void printTime(String type, Duration d) {
//        System.out.println("Type: " + type + " finished: " + d.toMinutesPart() + "m - " + d.toSecondsPart() + "s - " + d.toMillisPart() + "ms");
//    }

//    private static void loadData() {
//        if (Files.notExists(Paths.get(Configuration.instance.dataDir))) {
//            try {
//                Files.createDirectory(Paths.get(Configuration.instance.dataDir));
//                createData();
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.exit(1);
//            }
//        }
//        readFiles();
//
//    }

    private static void readFiles() {
        readPackages();
        readBoxes();
        readPallets();
        readTrucks();
    }

    private static Queue<String> readFileLines(File f){
        Queue<String> lines = new ArrayDeque<>();

        try (BufferedReader bw = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = bw.readLine()) != null){
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private static String[] removeSurroundingBrackets(String[] data){
        for(int i=0; i<data.length; i++){
            data[i] = data[i].trim().substring(1, data[i].length()-1).trim(); //remove []
        }

        return data;
    }

    private static void readPackages() {
        packagesMap = new HashMap<>(Configuration.instance.packageCount);
        Queue<String> packageData = readFileLines(new File(Configuration.instance.packageOutput));

        for(String line:packageData){
            String[] data = removeSurroundingBrackets(line.split(","));

            PackageType type = data[3].equals("NORMAL") ? PackageType.NORMAL :
                    data[3].equals("EXPRESS") ? PackageType.EXPRESS : PackageType.VALUE;

            packagesMap.put(data[0], new Package(data[0], data[1], Integer.parseInt(data[2]), type, Double.parseDouble(data[4])));
        }
    }


    private static void readBoxes() {
        boxesMap = new HashMap<>(Configuration.instance.boxesCount);
        Queue<String> boxData = readFileLines(new File(Configuration.instance.boxOutput));

        for(String line:boxData){
            String[] data = removeSurroundingBrackets(line.split(","));

            Box box = new Box(data[0]);

            for(int i=1; i<data.length; i++){
                box.addPackage(packagesMap.get(data[i]));
            }

            boxesMap.put(box.getId(), box);
        }
    }

    private static void readPallets() {
        palletsMap = new HashMap<>(Configuration.instance.palletCount);
        Queue<String> palletData = readFileLines(new File(Configuration.instance.palletOutput));

        for(String line: palletData){
            String[] data = removeSurroundingBrackets(line.split(","));

            if(palletsMap.containsKey(Integer.parseInt(data[0]))){
                palletsMap.get(Integer.parseInt(data[0])).addBox(boxesMap.get(data[3]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            }else{
                Pallet pallet = new Pallet(Integer.parseInt(data[0]));
                pallet.addBox(boxesMap.get(data[3]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                palletsMap.put(pallet.getId(), pallet);
            }
        }
    }

    private static void readTrucks() {
        trucksMap = new HashMap<>();
        Queue<String> truckData = readFileLines(new File(Configuration.instance.truckOutput));

        for (String line:truckData) {
            String[] data = removeSurroundingBrackets(line.split(","));
            boolean isLeft = data[1].equals("left");

            if(trucksMap.containsKey(data[0])){
                trucksMap.get(data[0]).addPallet(palletsMap.get(Integer.parseInt(data[3])), Integer.parseInt(data[2]), isLeft);
            }else{
                Truck truck = new Truck(data[0]);
                truck.addPallet(palletsMap.get(Integer.parseInt(data[3])), Integer.parseInt(data[2]), isLeft);
                trucksMap.put(data[0], truck);
            }
        }
    }

    public static void createData() {
        createPackages();
        createBoxes();
        createPallets();
        createTrucks();
    }

    public static boolean dataExists() {
        String[] neededFiles = {
                "base_box.csv",
                "base_package.csv",
                "base_pallet.csv",
                "base_truck.csv",
        };

        if (Files.notExists(Paths.get(Configuration.instance.dataDir))) {
            return false;
        }else {
            for (String s : neededFiles) {
                if (Files.notExists(Paths.get(Configuration.instance.dataDir + Configuration.instance.fileSeparator + s))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static Collection<Truck> loadTrucks() {
        if(packagesMap == null && boxesMap == null && palletsMap == null && trucksMap == null) {
            readFiles();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("CSV Data has been read, for a total of:").append(System.lineSeparator());
        builder.append("\t\t").append(packagesMap.size()).append(" packages;").append(System.lineSeparator());
        builder.append("\t\t").append(boxesMap.size()).append(" boxes;").append(System.lineSeparator());
        builder.append("\t\t").append(palletsMap.size()).append(" pallets;").append(System.lineSeparator());
        builder.append("\t\t").append(trucksMap.size()).append(" trucks;").append(System.lineSeparator());

        System.out.println(builder);
        return trucksMap.values();
    }
}
