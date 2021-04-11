package team16.location.logistics.temporarystorage;

import team16.data.datainstances.pallet.Pallet;

import java.util.Arrays;

public class TemporaryPalletStorage {
    private final TemporaryPalletStoragePosition[] temporaryPalletStoragePositions = new TemporaryPalletStoragePosition[5];

    public TemporaryPalletStorage() {
        for (int i = 0; i < temporaryPalletStoragePositions.length; i++)
            temporaryPalletStoragePositions[i] = new TemporaryPalletStoragePosition();
    }

    public boolean addPallet(Pallet pallet) {
        if (Arrays.stream(temporaryPalletStoragePositions).noneMatch(TemporaryPalletStoragePosition::hasRoom)) {
            return false;
        }

        for (TemporaryPalletStoragePosition position : temporaryPalletStoragePositions) {
            if (position.hasRoom()) {
                return position.addPallet(pallet);
            }
        }

        return false;
    }

    public boolean hasPallets() {
        for (TemporaryPalletStoragePosition position : temporaryPalletStoragePositions) {
            if (position.hasPallets()) {
                return true;
            }
        }

        return false;
    }

    public Pallet removePallet() {
        for (TemporaryPalletStoragePosition position : temporaryPalletStoragePositions) {
            if (position.hasPallets()) {
                return position.removePallet();
            }
        }

        return null;
    }

    public int getPalletCount() {
        int count = 0;

        for (TemporaryPalletStoragePosition position : temporaryPalletStoragePositions) {
            count += position.getPallets();
        }

        return count;
    }
}
