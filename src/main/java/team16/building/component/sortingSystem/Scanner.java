package team16.building.component.sortingSystem;

import team16.configuration.Configuration;
import team16.storage.packet.Package;

public class Scanner {

    public boolean checkPackage(Package packet) {
        return Configuration.INSTANCE.searchAlgorithm.checkPackage(packet);
    }
}
