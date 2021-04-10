package team16.storage.room.interim;

import team16.configuration.Configuration;
import team16.storage.pallet.Pallet;

import java.util.Arrays;

public class TemporaryPalletStorage {
    private final TemporaryPalletStoragePosition[] temporaryPalletStoragePositions = new TemporaryPalletStoragePosition[Configuration.instance.interimPositionsMax];

    public TemporaryPalletStorage() {
        for(int i=0; i< temporaryPalletStoragePositions.length; i++)
            temporaryPalletStoragePositions[i] = new TemporaryPalletStoragePosition();
    }

    public boolean addPallet(Pallet pallet) {
        if (Arrays.stream(temporaryPalletStoragePositions).noneMatch(TemporaryPalletStoragePosition::hasRoom)) {
            return false;
        }

        for(TemporaryPalletStoragePosition position : temporaryPalletStoragePositions){
            if(position.hasRoom()) {
                return position.addPallet(pallet);
            }
        }

        return false;

//        return Arrays.stream(temporaryPalletStoragePositions)
//                .filter(TemporaryPalletStoragePosition::hasRoom)
//                .findFirst()
//                .orElseThrow()
//                .addPallet(pallet);
    }

    public boolean hasPallets() {
        for(TemporaryPalletStoragePosition position : temporaryPalletStoragePositions){
            if(position.hasPallets())
                return true;
        }

        return false;
    }

    public Pallet removePallet() {
        for(TemporaryPalletStoragePosition position : temporaryPalletStoragePositions){
            if(position.hasPallets())
                return removePallet();
        }

        return null;
//        return Arrays.stream(temporaryPalletStoragePositions)
//                .filter(TemporaryPalletStoragePosition::hasPallets)
//                .findFirst()
//                .orElseThrow()
//                .removePallet();
    }
}
