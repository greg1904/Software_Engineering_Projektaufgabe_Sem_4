package team16.location.logistics.transportation;

import com.google.common.eventbus.Subscribe;
import team16.location.sorting.SortingSystem;
import team16.location.logistics.temporarystorage.TemporaryPalletStorage;
import team16.communication.events.StartRobotEvent;
import team16.data.datainstances.box.Box;
import team16.data.datainstances.box.BoxLayer;
import team16.data.datainstances.pallet.Pallet;
import team16.location.sorting.structure.PackageTrack;

public class Robot {
    private final SortingSystem sortingSystem;

    public Robot(SortingSystem sortingSystem) {
        this.sortingSystem = sortingSystem;
    }

    @Subscribe
    public void receive(StartRobotEvent event) {
        System.out.println("Robot is starting to working on TemporaryPalletStorage!");
        TemporaryPalletStorage storage = sortingSystem.getCenter().getTemporaryPalletStorage();
        System.out.println("Pallet count: " + storage.getPalletCount());
        storage.printStorage();
        int palletCount = 0;
        while (storage.hasPallets()) {
            Pallet pallet = storage.removePallet();

            if(palletCount % 100 == 0) {
                System.out.println("Starting next Pallet: " + palletCount);
                System.out.println(pallet);
            }
            palletCount++;

            while (pallet != null && pallet.hasLoad()) {
                Box box = pallet.getNextBox();
                for (BoxLayer layer : box.getBoxLayers()) {
                    while (!layer.isEmpty()) {
                        for(PackageTrack packageTrack : sortingSystem.getPackageTracks()){
                            if(packageTrack.hasRoom() && !layer.isEmpty()){
                                packageTrack.addPackage(layer.getNextPackage());
                            }
                        }
//                        Objects.requireNonNull(Arrays.stream(system.getPackageTracks())
//                                .filter(PackageTrack::hasRoom)
//                                .findFirst()
//                                .orElse(null))
//                                .addPackage(layer.getNextPackage());
                    }
                }

                sortingSystem.getEmptyBoxesRoom().addBox(box);
            }

            sortingSystem.getEmptyPalletsRoom().addPallet(pallet);
        }

        System.out.println("TemporaryPalletStorage has been processed by Robots. " +
                 + sortingSystem.getEmptyBoxesRoom().getBoxes().size() + " empty Boxes have been created and " +
                 + sortingSystem.getEmptyPalletsRoom().getPallets().size() + " empty Pallets have been stored.");
    }
}
