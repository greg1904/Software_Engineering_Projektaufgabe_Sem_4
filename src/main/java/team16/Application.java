package team16;

import org.jetbrains.annotations.NotNull;
import team16.configuration.Configuration;
import team16.storage.box.Box;
import team16.storage.packet.Package;
import team16.storage.pallet.Pallet;
import team16.vehicle.Truck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {

    private List<Package> packages;
    private List<Box> boxes;
    private List<Pallet> pallets;

    public static void main(String[] args) {
        Application app = new Application();
        app.checkIfOutputDirExist();
        app.processPrint(
                app.processPackages(),
                app.processBoxes(),
                app.processPallets(),
                app.processTrucks()
        );
    }

    @NotNull
    private String processPackages() {
        long start = System.nanoTime();

        String packageString = createPackages();

        long now = System.nanoTime();
        printTime("Package", Duration.ofNanos(now - start));
        return packageString;
    }

    @NotNull
    private String processBoxes() {
        long start = System.nanoTime();

        String boxString = createBoxes();

        long now = System.nanoTime();
        printTime("Box", Duration.ofNanos(now - start));
        return boxString;
    }

    @NotNull
    private String processPallets() {
        long start = System.nanoTime();

        String palletString = createPallets();

        long now = System.nanoTime();
        printTime("Pallet", Duration.ofNanos(now - start));
        return palletString;
    }

    @NotNull
    private String processTrucks() {
        long start = System.nanoTime();

        String truckString = createTrucks();

        long now = System.nanoTime();
        printTime("Truck", Duration.ofNanos(now - start));
        return truckString;
    }

    private void processPrint(String packageString, String boxString, String palletString, String truckString) {
        long start = System.nanoTime();
        File packageFile = new File(Configuration.INSTANCE.packageOutput);
        File boxFile = new File(Configuration.INSTANCE.boxOutput);
        File palletFile = new File(Configuration.INSTANCE.palletOutput);
        File truckFile = new File(Configuration.INSTANCE.truckOutput);

        print(packageFile, packageString);
        print(boxFile, boxString);
        print(palletFile, palletString);
        print(truckFile, truckString);

        long now = System.nanoTime();
        printTime("Print", Duration.ofNanos(now - start));
    }

    @NotNull
    private String createPackages() {
        packages = IntStream.range(0, Configuration.INSTANCE.packageCount)
                .parallel().mapToObj(i -> new Package())
                .collect(Collectors.toCollection(() -> new ArrayList<>(Configuration.INSTANCE.packageCount)));
        Collections.shuffle(packages);
        Random r = new Random();
        IntStream.range(0, 4).forEach(i -> packages.get(r.nextInt(packages.size())).addContent("exp!os:ve"));

        StringBuilder packageBuilder = new StringBuilder(Configuration.INSTANCE.packageContentSize[0] *
                Configuration.INSTANCE.packageContentSize[1] *
                Configuration.INSTANCE.packageContentSize[2] *
                Configuration.INSTANCE.packageCount);
        packages.forEach(pack -> {
            packageBuilder.append(String.format(Locale.US, "[%s],[%s],[%05d],[%s],[%.2f]",
                    pack.getId(), pack.getContentAsString(), pack.getZipCode(), pack.getType().name(), pack.getWeight()));
            packageBuilder.append(System.lineSeparator());
        });
        return packageBuilder.toString();
    }

    @NotNull
    private String createBoxes() {
        boxes = IntStream.range(0, Configuration.INSTANCE.boxesCount)
                .mapToObj(i -> new Box())
                .collect(Collectors.toCollection(() -> new ArrayList<>(Configuration.INSTANCE.boxesCount)));
        StringBuilder boxBuilder = new StringBuilder();
        Iterator<Package> iterator = packages.iterator();
        boxes.forEach(box -> {
            boxBuilder.append(String.format("[%s]", box.getId()));
            while (box.hasRoom() && iterator.hasNext()) {
                Package pack = iterator.next();
                box.addPackage(pack);
                boxBuilder.append(String.format(",[%s]", pack.getId()));
            }
            boxBuilder.append(System.lineSeparator());
        });
        return boxBuilder.toString();
    }

    @NotNull
    private String createPallets() {
        pallets = IntStream.range(0, Configuration.INSTANCE.palletCount)
                .mapToObj(i -> new Pallet())
                .collect(Collectors.toCollection(() -> new ArrayList<>(Configuration.INSTANCE.palletCount)));
        StringBuilder palletBuilder = new StringBuilder();
        Iterator<Box> iterator = boxes.iterator();
        pallets.forEach(pallet -> {
            while (pallet.hasRoom() && iterator.hasNext()) {
                Box box = iterator.next();
                pallet.addBox(box);
                palletBuilder.append(String.format("[%d],[%d],[%d],[%s]", pallet.getId(), pallet.getPositionIndex(box), pallet.getLayerIndex(box), box.getId()));
                palletBuilder.append(System.lineSeparator());
            }
        });
        return palletBuilder.toString();
    }

    @NotNull
    private String createTrucks() {
        List<Truck> trucks = IntStream.range(0, Configuration.INSTANCE.truckCount)
                .mapToObj(i -> new Truck())
                .collect(Collectors.toCollection(() -> new ArrayList<>(Configuration.INSTANCE.truckCount)));
        StringBuilder truckBuilder = new StringBuilder();
        Iterator<Pallet> iterator = pallets.iterator();
        trucks.forEach(truck -> {
            while (truck.hasRoom() && iterator.hasNext()) {
                Pallet pallet = iterator.next();
                truck.addPallet(pallet);
                int[] positions = truck.getPosition(pallet);
                truckBuilder.append(String.format("[%s],[%s],[%d],[%d]", truck.getId(), positions[0] == 0 ? "left" : "right", positions[1], pallet.getId()));
                truckBuilder.append(System.lineSeparator());
            }
        });
        return truckBuilder.toString();
    }

    private void print(File f, String s) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f), s.length())) {
            bw.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printTime(String type, Duration d) {
        System.out.printf("%s finished: %02d:%02d.%03d" + System.lineSeparator(), type, d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart());
    }

    private void checkIfOutputDirExist() {
        if (Files.notExists(Paths.get(Configuration.INSTANCE.outputDir))) {
            try {
                Files.createDirectory(Paths.get(Configuration.INSTANCE.outputDir));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
