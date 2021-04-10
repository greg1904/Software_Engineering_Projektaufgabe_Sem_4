package team16.data.datainstances.box;

import team16.data.utils.IdGenerator;
import team16.data.datainstances.packages.Package;
import team16.base.Configuration;

import java.util.Arrays;

public class Box {
    private final BoxLayer[] boxLayers = new BoxLayer[Configuration.instance.boxLayerCount];
    private final String id;

    public Box() {
        this(IdGenerator.getId(Configuration.instance.boxIdLength));
    }

    public Box(String id) {
        for(int i = 0; i< boxLayers.length; i++)
            boxLayers[i] = new BoxLayer();

        this.id = id;
    }

    public boolean addPackage(Package packet) {
        if (hasRoom()) {
            Arrays.stream(boxLayers).filter(x -> !x.isFull()).findFirst().orElseThrow().addPackage(packet);
            return true;
        }

        return false;
    }

    public boolean hasRoom() {
        for(int i = 0; i< boxLayers.length; i++){
            if(!boxLayers[i].isFull()){
                return true;
            }
        }

        return false;
    }

    public BoxLayer[] getBoxLayers() {
        return boxLayers;
    }

    public String getId() {
        return id;
    }

}
