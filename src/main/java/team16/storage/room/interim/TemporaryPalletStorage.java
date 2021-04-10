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
        System.out.println("checking pallets");
        int count = 0;
        for(TemporaryPalletStoragePosition position : temporaryPalletStoragePositions){
            if(position.hasPallets()) {
                System.out.println("Pallet has been found! pos: " + count);
                return true;
            }

            count++;
        }

        return false;
    }

    public Pallet removePallet() {
        System.out.println("##Removing Pallet Main");
        for(TemporaryPalletStoragePosition position : temporaryPalletStoragePositions){
            if(position.hasPallets()) {
                System.out.println("#Position has pallets - " + position.getPalletsString());
                return position.removePallet();
            }
        }

        return null;
//        return Arrays.stream(temporaryPalletStoragePositions)
//                .filter(TemporaryPalletStoragePosition::hasPallets)
//                .findFirst()
//                .orElseThrow()
//                .removePallet();
    }

    public int getPalletCount() {
        int count = 0;

        for(TemporaryPalletStoragePosition position : temporaryPalletStoragePositions){
            count += position.getPallets();
        }

        return count;
    }

    public void printStorage() {
        System.out.println("####");
        for(TemporaryPalletStoragePosition position : temporaryPalletStoragePositions){
            System.out.println(position.getPalletsString());
            System.out.println("---");
        }
        System.out.println("####");
    }
}
