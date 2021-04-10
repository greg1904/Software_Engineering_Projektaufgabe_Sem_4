package team16.building.component.sortingSystem;

import com.google.common.eventbus.Subscribe;
import team16.event.StartRobotEvent;
import team16.storage.box.Box;
import team16.storage.box.BoxLayer;
import team16.storage.pallet.Pallet;
import team16.storage.room.interim.TemporaryPalletStorage;

public class Robot {
    private final SortingSystem sortingSystem;

    public Robot(SortingSystem sortingSystem) {
        this.sortingSystem = sortingSystem;
    }

    @Subscribe
    public void receive(StartRobotEvent event) {
        System.out.println("Robot is starting to working on TemporaryPalletStorage!");
        TemporaryPalletStorage storage = sortingSystem.getCenter().getTemporaryPalletStorage();
        while (storage.hasPallets()) {
            Pallet pallet = storage.removePallet();
            while (pallet.hasLoad()) {
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
