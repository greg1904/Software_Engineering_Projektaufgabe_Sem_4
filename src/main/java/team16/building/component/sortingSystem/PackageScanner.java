package team16.building.component.sortingSystem;

import team16.configuration.Configuration;
import team16.configuration.SearchAlgorithm;
import team16.storage.packet.Package;

public class PackageScanner {
    public boolean checkPackage(Package packet) {
        return Configuration.instance.searchAlgorithm.checkPackage(packet);
    }
}