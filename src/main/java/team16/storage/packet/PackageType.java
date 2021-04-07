package team16.storage.packet;

public enum PackageType {
    NORMAL, EXPRESS, VALUE;

    @Override
    public String toString() {
        String name = name();
        return name.substring(0, 1).toUpperCase() +
                name.substring(1).toLowerCase();//Capitalize first Letter
    }
}
