package team16.storage.box;

import team16.configuration.Configuration;
import team16.storage.StoragePool;
import team16.storage.packet.Package;

import java.util.Arrays;

public class Box {

    private final BoxLayer[] layer = new BoxLayer[Configuration.INSTANCE.boxLayerCount];
    private final String id;

    public Box() {
        this(StoragePool.INSTANCE.getId(Configuration.INSTANCE.boxIdLength));
    }

    public Box(String id) {
        Arrays.setAll(layer, i -> new BoxLayer());
        this.id = id;
    }

    public void addPackage(Package packet) {
        if (hasRoom()) {
            Arrays.stream(layer).filter(BoxLayer::hasRoom).findFirst().orElseThrow().addPackage(packet);
        }
    }

    public boolean hasRoom() {
        return Arrays.stream(layer).anyMatch(BoxLayer::hasRoom);
    }

    public BoxLayer[] getLayer() {
        return layer;
    }

    public String getId() {
        return id;
    }

}
