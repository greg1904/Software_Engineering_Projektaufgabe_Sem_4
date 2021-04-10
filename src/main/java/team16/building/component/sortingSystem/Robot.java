package team16.building.component.sortingSystem;

import com.google.common.eventbus.Subscribe;
import team16.event.StartRobotEvent;
import team16.storage.box.Box;
import team16.storage.box.BoxLayer;
import team16.storage.pallet.Pallet;
import team16.storage.room.interim.InterimPalletStorage;

import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class Robot {

    private final SortingSystem system;

    public Robot(SortingSystem system) {
        this.system = system;
    }

    @Subscribe
    public void receive(StartRobotEvent event) {
        System.out.println("Robot starts working on Interim-Storage");
        InterimPalletStorage storage = system.getCenter().getInterimPalletStorage();
        while (storage.hasPallets()) {
            Pallet pallet = storage.removePallet();
            while (pallet.hasLoad()) {
                Box box = pallet.getNextBox();
                for (BoxLayer layer : box.getBoxLayers()) {
                    while (layer.hasLoad()) {
                        Objects.requireNonNull(Arrays.stream(system.getPackageTracks())
                                .filter(PackageTrack::hasRoom)
                                .findFirst()
                                .orElse(null))
                                .addPackage(layer.getNextPackage());
                    }
                }
                system.getEmptyBoxesRoom().addBox(box);
            }
            system.getEmptyPalletsRoom().addPallet(pallet);
        }
        System.out.println("Storage done! Empty Boxes: " + system.getEmptyBoxesRoom().getBoxes().size() + "; Empty Pallets: " + system.getEmptyPalletsRoom().getPallets().size());
    }
}
