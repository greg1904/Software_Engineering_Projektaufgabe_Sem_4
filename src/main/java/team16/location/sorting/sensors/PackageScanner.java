package team16.location.sorting.sensors;

import team16.base.Configuration;
import team16.data.datainstances.packages.Package;

public class PackageScanner {
    public boolean checkPackage(Package packet) {
        return Configuration.instance.searchAlgorithm.checkPackage(packet);
    }
}
